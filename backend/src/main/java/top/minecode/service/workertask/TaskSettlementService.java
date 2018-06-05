package top.minecode.service.workertask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.jvm.hotspot.jdi.ArrayReferenceImpl;
import top.minecode.dao.worker.WorkerInfoDao;
import top.minecode.dao.workertask.TaskSettlementDao;
import top.minecode.domain.tag.SubTaskResult;
import top.minecode.domain.tag.TaskResult;
import top.minecode.domain.task.SubTaskParticipationState;
import top.minecode.domain.task.SubTaskState;
import top.minecode.domain.task.TaskState;
import top.minecode.domain.task.TaskType;
import top.minecode.domain.utils.Pair;
import top.minecode.po.task.TaskPO;
import top.minecode.po.worker.SubTaskParticipationPO;
import top.minecode.po.worker.WorkerPO;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created on 2018/6/4.
 * Description:
 *
 * @author iznauy
 */
@Service
public class TaskSettlementService {

    private WorkerInfoDao infoDao;

    private TaskSettlementDao settlementDao;

    public WorkerInfoDao getInfoDao() {
        return infoDao;
    }

    @Autowired
    public void setInfoDao(WorkerInfoDao infoDao) {
        this.infoDao = infoDao;
    }

    public TaskSettlementDao getSettlementDao() {
        return settlementDao;
    }

    @Autowired
    public void setSettlementDao(TaskSettlementDao settlementDao) {
        this.settlementDao = settlementDao;
    }

    public void settleTasks() {
        List<TaskPO> waitingForSettledTasks = settlementDao.getCanSettledTasks(); // 这儿只是考虑了让要去结算的任务，等会要考虑会过期的小任务
        for (TaskPO taskPO: waitingForSettledTasks)
            settleTask(taskPO);
    }

    private void settleTask(TaskPO taskPO) {


        List<String> participators = taskPO.getParticipators();
        List<WorkerPO> workers = infoDao.getListByEmails(participators);

        Map<String, WorkerPO> emailToWorker = workers.stream().collect(Collectors.toMap(WorkerPO::getEmail, e -> e));

        // 该任务所有的下属参与
        List<SubTaskParticipationPO> subTaskParticipationPOS = settlementDao.getParticipationByTaskId(taskPO.getId());

        Map<String, List<SubTaskParticipationPO>> emailToParticipation = subTaskParticipationPOS.stream()
                .collect(Collectors.groupingBy(SubTaskParticipationPO::getEmail));

        Set<TaskType> types = taskPO.getSpecificTasks().keySet();

        Map<TaskType, SubTaskResult> subResults = new HashMap<>();

        for (TaskType type: types)
            subResults.put(type, new SubTaskResult());

        Map<String, Pair<Double, Integer>> emailToErrorRate = new HashMap<>();
        for (WorkerPO workerPO: workers) {
            emailToErrorRate.put(workerPO.getEmail(), new Pair<>(1.0, 0));
        }

        for (String email: emailToParticipation.keySet()) {
            WorkerPO workerPO = emailToWorker.get(email);
            List<SubTaskParticipationPO> participationPOS = emailToParticipation.get(email);
            if (workerPO == null) { // 代表这是客服人员做的，直接加进去就好
                for (SubTaskParticipationPO po: participationPOS) {
                    if (po.getState() != SubTaskParticipationState.FINISHED) // 客服没做完的就跳过
                        continue;
                    TaskType taskType = po.getSubTaskType();
                    subResults.get(taskType).extendResult(po.getTags());
                    taskPO.addActualDollars(po.getPicAmount() * TaskType.getPrice(taskType));
                    // 如果是客服做的，只需要扣除一部分钱就好
                }
            } else { // 否则就是工人做的，不仅要对客服没有结算的任务进行质量评估，还有更改用户的部分属性
                List<Pair<Double, Integer>> correctRateAndPicAmount = new ArrayList<>();
                for (SubTaskParticipationPO po: participationPOS) {
                    TaskType taskType = po.getSubTaskType();
                    if (po.getState() == SubTaskParticipationState.EXPIRED) {
                        //过期会受到严厉的惩罚
                        correctRateAndPicAmount.add(new Pair<>(0.0, po.getPicAmount()));
                        continue;
                    }
                    if (po.getState() == SubTaskParticipationState.FINISHED) {
                        // 事实上只有过期和完成两种状态，因为我们是先过期，然后在进行完成检测
                        // 对于一个已经完成的任务，那么要检查是否被接受
                        if (!po.isEvaluated()) { // 如果没有人给过评价，那么认为这个任务是完全ok的
                            po.setEvaluated(true);
                            po.setErrorRate(0.0);
                            po.setAccept(true);

                            double earnedDollars = po.getPicAmount() * TaskType.getPrice(taskType);

                            po.setEarnedDollars(earnedDollars);
                            taskPO.addActualDollars(earnedDollars);
                            workerPO.addDollars(earnedDollars);
                        }
                        correctRateAndPicAmount.add(new Pair<>(po.getErrorRate(), po.getPicAmount()));
                        if (po.isAccept()) {
                            subResults.get(taskType).extendResult(po.getTags());
                        }
                    }
                }
                // 统计用户正确率
                int totalPics = correctRateAndPicAmount.stream().mapToInt(e -> e.r).sum();
                double errorPics = correctRateAndPicAmount.stream().mapToDouble(e -> e.l * e.r).sum();
                double correctRate = (totalPics - errorPics) / totalPics;
                emailToErrorRate.put(email, new Pair<>(correctRate, (int) (totalPics - errorPics)));
            }
        }

        // 接着对错误率进行排序，对每个人进行分数加减
        // 如果排序很靠前，就多加分，排序很靠后，就减分
        // 此外，要根据用户做的数量的不同，来决定
        List<Pair<String, Pair<Double, Integer>>> userTaskInfo = new ArrayList<>(emailToErrorRate.entrySet()).stream()
                .map(e -> new Pair<>(e.getKey(), e.getValue())).collect(Collectors.toList());

        userTaskInfo.sort((o1, o2) -> {
            if (o2.r.l > o1.r.l)
                return 1;
            else if (o2.r.l.equals(o1.r.l))
                return 0;
            else
                return -1;
        });

        int participatedUserAmount = userTaskInfo.size();
        for (int i = 0; i < participatedUserAmount; i++) {
            Pair<String, Pair<Double, Integer>> userInfo = userTaskInfo.get(i);
            double standardScore = (2 * i + 1) / (participatedUserAmount * 2.0) * 6 - 3;
            double scoreChange = standardScore * userInfo.r.r / 10;
            WorkerPO currentWorker = emailToWorker.get(userInfo.l);
            currentWorker.changeScore(scoreChange);

            // 把ongoing 变成 finished


        }


        // 保存结果到文件中
        TaskResult result = new TaskResult(new ArrayList<>(subResults.values()));
        String filePath = taskPO.getResultFilePath(); // 用来存储标注结果的文件


    }


}

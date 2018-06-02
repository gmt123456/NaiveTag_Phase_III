package top.minecode.service.staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.minecode.dao.staff.StaffDao;
import top.minecode.dao.staff.TaskCheckDao;
import top.minecode.dao.worker.WorkerInfoDao;
import top.minecode.dao.workertask.SubTaskDao;
import top.minecode.dao.workertask.TaskDao;
import top.minecode.dao.workertask.TaskParticipationDao;
import top.minecode.domain.tag.TagResult;
import top.minecode.domain.task.SubTask;
import top.minecode.domain.task.SubTaskState;
import top.minecode.domain.task.TaskType;
import top.minecode.domain.taskcheck.SubCheckTaskState;
import top.minecode.po.task.SubCheckTaskPO;
import top.minecode.po.task.SubTaskPO;
import top.minecode.po.task.TaskPO;
import top.minecode.po.worker.SubTaskParticipationPO;
import top.minecode.po.worker.WorkerPO;
import top.minecode.web.common.WebConfig;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created on 2018/6/2.
 * Description:
 *
 * @author iznauy
 */
@Service
public class StaffSubTaskCheckService {

    private WorkerInfoDao workerInfoDao;

    private TaskCheckDao checkDao;

    private StaffDao staffDao;

    private SubTaskDao subTaskDao;

    private TaskParticipationDao participationDao;

    private TaskDao taskDao;

    public SubTaskDao getSubTaskDao() {
        return subTaskDao;
    }

    @Autowired
    public void setSubTaskDao(SubTaskDao subTaskDao) {
        this.subTaskDao = subTaskDao;
    }

    public TaskDao getTaskDao() {
        return taskDao;
    }

    @Autowired
    public void setTaskDao(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public WorkerInfoDao getWorkerInfoDao() {
        return workerInfoDao;
    }

    @Autowired
    public void setWorkerInfoDao(WorkerInfoDao workerInfoDao) {
        this.workerInfoDao = workerInfoDao;
    }

    public TaskCheckDao getCheckDao() {
        return checkDao;
    }

    public StaffDao getStaffDao() {
        return staffDao;
    }

    public TaskParticipationDao getParticipationDao() {
        return participationDao;
    }

    @Autowired
    public void setParticipationDao(TaskParticipationDao participationDao) {
        this.participationDao = participationDao;
    }

    @Autowired
    public void setCheckDao(TaskCheckDao checkDao) {
        this.checkDao = checkDao;
    }

    @Autowired
    public void setStaffDao(StaffDao staffDao) {
        this.staffDao = staffDao;
    }

    // 获取没有mark的一张新图片
    public String morePic(int participationId) {
        SubCheckTaskPO subCheckTaskPO = checkDao.getSubCheckById(participationId);
        Set<String> markedPics = subCheckTaskPO.getPicAccept().keySet();
        Set<String> allPics = subCheckTaskPO.getSampledTags().keySet();
        if (allPics.size() == markedPics.size()) {
            commit(subCheckTaskPO);
            return null;
        }
        List<String> diff = allPics.stream().filter(markedPics::contains)
                .collect(Collectors.toList());
        Random random = new Random();
        int index = random.nextInt(diff.size());
        return diff.get(index);
    }

    public TagResult getLabelInfo(int participationId, String url) {
        SubCheckTaskPO subCheckTaskPO = checkDao.getSubCheckById(participationId);
        return WebConfig.getGson().fromJson(subCheckTaskPO.getSampledTags().get(url), TagResult.class);
    }

    public void mark(int participationId, String url, boolean accept) {
        SubCheckTaskPO subCheckTaskPO = checkDao.getSubCheckById(participationId);
        subCheckTaskPO.getPicAccept().put(url, accept);
        checkDao.updateSubCheck(subCheckTaskPO);
    }

    private void commit(SubCheckTaskPO subCheckTaskPO) {
        subCheckTaskPO.setCheckTaskState(SubCheckTaskState.finished);
        Map<String, Boolean> resultMap = subCheckTaskPO.getPicAccept();

        int acceptCount = 0;
        Collection<Boolean> values =  resultMap.values();
        for (Boolean value: values)
            if (value)
                acceptCount += 1;

        double correctRate = acceptCount * 1.0 / subCheckTaskPO.getPicAmount();
        double tolerantRate = subCheckTaskPO.getAcceptRate();

        boolean accept = correctRate >= tolerantRate;

        SubTaskParticipationPO subTaskParticipationPO =
                participationDao.getSubTaskParticipationById(subCheckTaskPO.getSubPartId());

        subTaskParticipationPO.setAccept(accept);
        subTaskParticipationPO.setEvaluated(true);
        subTaskParticipationPO.setErrorRate(1 - correctRate);

        String email = subTaskParticipationPO.getEmail();

        if (accept) { // 如果这个任务正确率ok，那么就给他结算了，让他领钱

            TaskPO taskPO = taskDao.getTaskById(subCheckTaskPO.getTaskId());

            double earnedDollars = TaskType.getPrice(subTaskParticipationPO.getSubTaskType())
                    * subTaskParticipationPO.getPicAmount() * taskPO.getPrizeRate() * correctRate;

            double preDollars = taskPO.getActualDollars() == null ? 0.0 : taskPO.getActualDollars();
            preDollars += earnedDollars;
            taskPO.setActualDollars(preDollars);
            taskDao.update(taskPO);

            subTaskParticipationPO.setEarnedDollars(earnedDollars);

            WorkerPO workerPO = workerInfoDao.getWorkerPOByEmail(email);
            workerPO.setDollars(workerPO.getDollars() + earnedDollars);

            workerInfoDao.updateWorkPO(workerPO);

        } else {
            subTaskParticipationPO.setEarnedDollars(0.0); // need to finish
        }

        SubTaskPO subTask = subTaskDao.getSubTaskById(subTaskParticipationPO.getSubTaskId());

        if (accept) {
            subTask.setSubTaskState(SubTaskState.FINISHED);
        } else {
            subTask.setSubTaskState(SubTaskState.COMMON);
            subTask.setCurrentDoingWorker(null);
        }

        subTaskDao.updateSubTask(subTask);
        participationDao.updateSubTaskParticipation(subTaskParticipationPO);

    }
}

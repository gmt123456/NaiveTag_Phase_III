package top.minecode.dao.worker;

import org.hibernate.Query;
import org.springframework.stereotype.Service;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.domain.task.TaskType;
import top.minecode.domain.user.worker.WorkerCommitmentLog;
import top.minecode.po.log.JoinTaskLogPO;
import top.minecode.po.task.SubTaskPO;
import top.minecode.po.task.TaskPO;
import top.minecode.po.worker.FinishedTaskParticipationPO;
import top.minecode.po.worker.OnGoingTaskParticipationPO;
import top.minecode.po.worker.WorkerPO;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
@Service
public class WorkerInfoDao {

    private CommonOperation<WorkerPO> workerPOHelper = new CommonOperation<>(WorkerPO.class.getName());

    private CommonOperation<FinishedTaskParticipationPO> finishParticipationHelper =
            new CommonOperation<>(FinishedTaskParticipationPO.class.getName());

    private CommonOperation<OnGoingTaskParticipationPO> onGoingTaskParticipationHelper =
            new CommonOperation<>(OnGoingTaskParticipationPO.class.getName());
    private CommonOperation<TaskPO> taskOperation = new CommonOperation<>(TaskPO.class);


    public WorkerPO getWorkerPOByEmail(String email) {
        return workerPOHelper.getBySingleField("email", email);
    }


    public boolean addWorker(WorkerPO worker) {
        return workerPOHelper.add(worker);
    }

    public boolean updateWorkPO(WorkerPO workerPO) {
        return workerPOHelper.update(workerPO);
    }

    public List<FinishedTaskParticipationPO> getFinishedTasks(String email) {
        WorkerPO worker = getWorkerPOByEmail(email);
        List<Integer> finishedParticipation = worker.getFinishedTaskParticipation();
        return finishParticipationHelper.getValuesInSpecificSet(finishedParticipation, "id");
    }

    public List<OnGoingTaskParticipationPO> getOnGoingTasks(String email) {
        WorkerPO worker = getWorkerPOByEmail(email);
        List<Integer> onGoingParticipation = worker.getOnGoingTaskParticipation();
        return onGoingTaskParticipationHelper.getValuesInSpecificSet(onGoingParticipation, "id");
    }

    public List<Integer> getParticipatedTasks(String email) {
        WorkerPO worker = getWorkerPOByEmail(email);
        List<Integer> finishedParticipation = worker.getFinishedTaskParticipation();
        List<Integer> onGoingParticipation = worker.getOnGoingTaskParticipation();
        finishedParticipation.addAll(onGoingParticipation);

        return finishedParticipation;
    }

    public boolean persistWorker(WorkerPO worker) {
        return workerPOHelper.update(worker);
    }

    public List<WorkerPO> getAll() {
        return workerPOHelper.getAll();
    }

    public List<WorkerPO> getListByEmails(List<String> emails) {
        return workerPOHelper.getValuesInSpecificSet(emails, "email");
    }

    public List<WorkerCommitmentLog> getWorkerCommitment(String email) {
        String hql = "select new top.minecode.domain.user.worker.WorkerCommitmentLog( " +
                " cast(count(t) as int), max(t.commitTime), t.taskId, p.joinTime) " +
                " from TaskCommitmentLogPO t, JoinTaskLogPO p where t.userEmail=:mail " +
                " and p.userEmail = p.userEmail and p.taskId=t.taskId group by t.taskId, p.joinTime";

        return CommonOperation.template(session -> {
            Query query = session.createQuery(hql);
            query.setParameter("mail", email);
            //noinspection unchecked
            return (List<WorkerCommitmentLog>) query.list();
        });
    }

    public List<Double> getWorkerCommitmentSpeed() {
        return getAll().stream().map(e -> getWorkerCommitmentSpeed(e.getEmail())).collect(Collectors.toList());
    }

    public Map<TaskType, Double> getWorkerSpeedInPicturesByTaskType() {
        List<WorkerPO> workers = getAll();
        List<TaskType> taskTypes = TaskType.getAll();

        Function<TaskType, Double> expectedSpeedFunction =
                t -> workers.stream().mapToDouble(e -> getWorkerSpeedInPictures(e.getEmail(), t))
                        .average().orElse(0.);
        return taskTypes.stream().collect(Collectors.toMap(Function.identity(), expectedSpeedFunction));
    }

    public Map<String, Double> getWorkerSpeedInPictures() {
        List<WorkerPO> workers = getAll();
        return workers.stream().collect(Collectors.toMap(WorkerPO::getEmail,
                w -> getWorkerSpeedInPictures(w.getEmail())));
    }

    private double getWorkerSpeedInPictures(String email) {
        String hql = "select new map(t.taskId as taskId, s.id as subTaskId)" +
                " from TaskCommitmentLogPO t, SubTaskPO s where t.userEmail=:mail";

        List<Map> queryResult = CommonOperation.template(session -> {
            Query query = session.createQuery(hql);
            query.setParameter("mail", email);
            //noinspection unchecked
            return (List<Map>) query.list();
        });

        Map<Integer, Integer> picNumMap = transformListMap(queryResult);

        List<WorkerCommitmentLog> logs = getWorkerCommitment(email);
        logs.forEach(log -> log.setNumber(picNumMap.get(log.getTaskId()).intValue()));

        return logs.size() == 0 ? 0. : logs.stream().mapToDouble(WorkerCommitmentLog::getSpeed)
                .average().orElseThrow(ArithmeticException::new);
    }

    // Return speed in pictures per day
    public double getWorkerSpeedInPictures(String email, TaskType taskType) {
        String hql = "select new map(t.taskId as taskId, s.id as subTaskId)" +
                " from TaskCommitmentLogPO t, SubTaskPO s where s.taskType=:type " +
                " and t.userEmail=:mail";

        List<Map> queryResult = CommonOperation.template(session -> {
            Query query = session.createQuery(hql);
            query.setParameter("mail", email)
                    .setParameter("type", taskType);
            //noinspection unchecked
            return (List<Map>) query.list();
        });

        // Change pictureNumMap to a HashMap
        Map<Integer, Integer> picNumMap = transformListMap(queryResult);

        // Transform to WorkerCommitmentLog
        List<WorkerCommitmentLog> logs = getWorkerCommitment(email)
                .stream().filter(log -> isTaskType(log.getTaskId(), taskType))
                .collect(Collectors.toList());
        logs.forEach(log -> log.setNumber(picNumMap.get(log.getTaskId()).intValue()));

        return logs.size() == 0 ? 0. : logs.stream().mapToDouble(WorkerCommitmentLog::getSpeed)
                .average().orElseThrow(ArithmeticException::new);
    }

    private Map<Integer, Integer> transformListMap(List<Map> queryResult) {
        Map<Integer, Integer> picNumMap = new HashMap<>();
        CommonOperation<SubTaskPO> subTaskOperation = new CommonOperation<>(SubTaskPO.class);
        for (Map data : queryResult) {
            int picNum = subTaskOperation
                    .getBySingleField("id", data.get("subTaskId")).getPicList().size();
            int taskId = (int) data.get("taskId");
            picNumMap.merge(taskId, picNum, (a, b) -> a + b);
        }
        return picNumMap;
    }

    private boolean isTaskType(int taskId, TaskType taskType) {
        TaskPO taskPO = taskOperation.getBySingleField("id", taskId);
        return taskPO.getSpecificTasks().keySet().contains(taskType);
    }

    /**
     * @param email worker's email
     * @return worker's speed: commitment times per day
     * @throws ArithmeticException if any worker's speed is Nan
     */
    private double getWorkerCommitmentSpeed(String email) {
        List<WorkerCommitmentLog> commitmentLogs = getWorkerCommitment(email);
        return commitmentLogs.size() == 0 ? 0.0 : commitmentLogs.stream().mapToDouble(WorkerCommitmentLog::getSpeed)
                .average().orElseThrow(ArithmeticException::new);
    }

    public double getWorkerSpeedRankRate(String email) {
        String hql = "select t.email from WorkerPO t where t.email<>?";
        List<String> emails = CommonOperation.executeSQL(String.class, hql, email);

        List<Double> otherSpeeds = emails.stream().map(this::getWorkerCommitmentSpeed).sorted().collect(Collectors.toList());
        double thisSpeed = getWorkerCommitmentSpeed(email);
        for (int i = 0; i < otherSpeeds.size(); i++) {
            if (thisSpeed < otherSpeeds.get(i))
                return (i + 1) * 1. / (otherSpeeds.size() + 1);
        }

        return 1. / (otherSpeeds.size() + 1);  // Means the first
    }

    public void addJoinTaskLog(String email, int taskId) {
        JoinTaskLogPO logPO = new JoinTaskLogPO(email, taskId, new Date());
        CommonOperation.template(session -> {
            session.persist(logPO);
            session.flush();
        });
    }
}

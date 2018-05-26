package top.minecode.service.workertask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.minecode.dao.log.WorkerLogDao;
import top.minecode.dao.worker.WorkerInfoDao;
import top.minecode.dao.workertask.SubTaskDao;
import top.minecode.dao.workertask.TaskDao;
import top.minecode.dao.workertask.TaskParticipationDao;
import top.minecode.domain.task.*;
import top.minecode.domain.user.worker.Division;
import top.minecode.po.log.WorkerViewLogPO;
import top.minecode.po.task.SubTaskPO;
import top.minecode.po.task.TaskPO;
import top.minecode.po.worker.FinishedTaskParticipationPO;
import top.minecode.po.worker.OnGoingTaskParticipationPO;
import top.minecode.po.worker.SubTaskParticipationPO;
import top.minecode.po.worker.WorkerPO;

import java.util.*;

/**
 * Created on 2018/5/21.
 * Description:
 *
 * @author iznauy
 */
@Service
public class WorkerSpecificTaskService {

    private WorkerInfoDao workerInfoDao;

    private TaskDao taskDao;

    private SubTaskDao subTaskDao;

    private TaskParticipationDao participationDao;

    private WorkerLogDao logDao;

    public WorkerLogDao getLogDao() {
        return logDao;
    }

    @Autowired
    public void setLogDao(WorkerLogDao logDao) {
        this.logDao = logDao;
    }

    public SubTaskDao getSubTaskDao() {
        return subTaskDao;
    }

    @Autowired
    public void setSubTaskDao(SubTaskDao subTaskDao) {
        this.subTaskDao = subTaskDao;
    }

    public TaskParticipationDao getParticipationDao() {
        return participationDao;
    }

    @Autowired
    public void setParticipationDao(TaskParticipationDao participationDao) {
        this.participationDao = participationDao;
    }

    public WorkerInfoDao getWorkerInfoDao() {
        return workerInfoDao;
    }

    @Autowired
    public void setWorkerInfoDao(WorkerInfoDao workerInfoDao) {
        this.workerInfoDao = workerInfoDao;
    }

    public TaskDao getTaskDao() {
        return taskDao;
    }

    @Autowired
    public void setTaskDao(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    private void addViewLog(String email, int taskId) {
        WorkerViewLogPO log = new WorkerViewLogPO(email, taskId, new Date());
        logDao.addViewLog(log);
    }

    public TaskSpecification getTaskDetail(String email, int taskId) {

        addViewLog(email, taskId);

        TaskPO taskPO = taskDao.getTaskById(taskId);
        boolean accepted = taskPO.getParticipators().stream().anyMatch(e -> e.equals(email));
        boolean canAccept = false;

        TaskState state = taskPO.getTaskState();
        Division requiredDivision = taskPO.getLowestDivision();

        WorkerPO workerPO = workerInfoDao.getWorkerPOByEmail(email);
        if (Division.difference(Division.convert(workerPO.getScore()), requiredDivision) >= 0 && !accepted
                && state == TaskState.ON_GOING)
            canAccept = true;

        String taskBackground = taskPO.getReadme();

        double earnedDollars = 0.0;
        double scoreChange = 0.0;

        if (accepted && state == TaskState.FINISHED) {
            List<FinishedTaskParticipationPO> finishedTaskParticipation = workerInfoDao.getFinishedTasks(email);
            for (FinishedTaskParticipationPO temp: finishedTaskParticipation) {
                if (temp.getTaskId() == taskId) {
                    earnedDollars = temp.getEarnedDollars();
                    scoreChange = temp.getScoreChange();
                    break;
                }
            }
        }


        return new TaskSpecification(Task.fromPO(taskPO), state, accepted,
                requiredDivision, taskBackground, canAccept,
                earnedDollars, scoreChange, taskPO.getBackgroundImage());
    }


    public SubTaskAcceptResponse acceptSubTask(String email, int taskId, int subTaskId, TaskType taskType) {
        TaskPO task = taskDao.getTaskById(taskId);
        if (task.getParticipators().stream().anyMatch(e -> e.equals(email)))
            return new SubTaskAcceptResponse(false, SubTaskAcceptResponse.UN_KNOWN);
        if (!task.getSpecificTasks().keySet().contains(taskType))
            return new SubTaskAcceptResponse(false, SubTaskAcceptResponse.UN_KNOWN);

        SubTaskPO subTaskPO = subTaskDao.getSubTaskById(subTaskId);

        if (subTaskPO == null || subTaskPO.getTaskType() != taskType || subTaskPO.getSubTaskState() != SubTaskState.COMMON)
            return new SubTaskAcceptResponse(false, SubTaskAcceptResponse.UN_KNOWN);


        List<OnGoingTaskParticipationPO> participation = workerInfoDao.getOnGoingTasks(email);

        OnGoingTaskParticipationPO partPO = null;

        for (OnGoingTaskParticipationPO po: participation)
            if (po.getTaskId() == taskId) {
                partPO = po;
                break;
            }

        if (partPO == null)
            return new SubTaskAcceptResponse(false, SubTaskAcceptResponse.UN_KNOWN);

        SubTaskParticipationPO participationPO = new SubTaskParticipationPO();
        participationPO.setTaskId(taskId);
        participationPO.setSubTaskType(taskType);
        participationPO.setCover(subTaskPO.getCover());
        participationPO.setPicAmount(subTaskPO.getPicList().size());
        participationPO.setState(SubTaskParticipationState.DOING);
        participationPO.setEmail(email);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, 3);

        participationPO.setExpiredDate(task.getEndDate().before(calendar.getTime()) ?
                task.getEndDate() : calendar.getTime());

        subTaskDao.addSubTaskParticipation(participationPO);

        Integer newId = participationPO.getId(); // 增加ID
        partPO.getParticipatedSubTaskResultIds().add(newId);
        participationDao.updateOnGoingTaskParticipation(partPO);

        subTaskPO.getCurrentDoingWorkers().add(email);

        if (subTaskPO.getCurrentDoingWorkers().size() + subTaskPO.getFinishedWorkers().size() == 3)
            subTaskPO.setSubTaskState(SubTaskState.LOCKED);

        subTaskDao.updateSubTask(subTaskPO);

        return new SubTaskAcceptResponse(true,null);
    }

    public SubTaskDetail getSubTaskDetail(String email, int taskId, int subTaskId, TaskType taskType) {
        SubTaskPO subTaskPO = subTaskDao.getSubTaskById(subTaskId);
        TaskPO taskPO = taskDao.getTaskById(taskId);

        String taskName = taskPO.getTaskName();
        String taskDescription = subTaskPO.getTaskDescription();

        SubTaskDetail subTaskDetail = null;
        if (subTaskPO.getFinishedWorkers().stream().anyMatch(e -> e.equals(email))) {
            // 接过的任务
            SubTaskParticipationState taskState = SubTaskParticipationState.DOING;
            List<String> allPics = subTaskPO.getPicList();
            List<String> unFinishedPics = new ArrayList<>();

            SubTaskParticipationPO subTaskParticipation = participationDao
                    .getWorkerSubTaskParticipation(email, taskId, subTaskId);

            Set<String> finishedPicsSet = subTaskParticipation.getTags().keySet();
            for (String path: allPics) {
                if (!finishedPicsSet.contains(path)) {
                    unFinishedPics.add(path);
                }
            }
            List<String> finishedPics = new ArrayList<>(finishedPicsSet);

            subTaskDetail = new AcceptedSubTask(taskId, subTaskId, taskState, taskName, taskType, taskDescription,
                    finishedPics, unFinishedPics, subTaskParticipation.getExpiredDate());

        } else {
            SubTaskParticipationState taskState = SubTaskParticipationState.UN_PART;
            subTaskDetail = new UnAcceptedSubTask(taskId, subTaskId, taskState, taskName, taskType, taskDescription,
                    subTaskPO.getPicList());

        }
        return subTaskDetail;
    }

}

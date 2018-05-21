package top.minecode.service.workertask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.minecode.dao.worker.WorkerInfoDao;
import top.minecode.dao.workertask.TaskDao;
import top.minecode.dao.workertask.TaskParticipationDao;
import top.minecode.domain.task.*;
import top.minecode.domain.user.worker.Division;
import top.minecode.domain.user.worker.Worker;
import top.minecode.po.task.TaskPO;
import top.minecode.po.worker.FinishedTaskParticipationPO;
import top.minecode.po.worker.WorkerPO;

import java.util.List;

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

    private TaskParticipationDao participationDao;

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

    public TaskSpecification getTaskDetail(String email, int taskId) {
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
                earnedDollars, scoreChange);
    }


    public SubTaskAcceptResponse acceptSubTask(String email, int taskId, int subTaskId, TaskType taskType) {
        
        return null;
    }

}

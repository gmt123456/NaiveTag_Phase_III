package top.minecode.dao.workertask;

import org.springframework.stereotype.Repository;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.domain.task.SubTaskParticipation;
import top.minecode.domain.task.SubTaskState;
import top.minecode.domain.task.Task;
import top.minecode.domain.task.TaskState;
import top.minecode.po.task.SubTaskPO;
import top.minecode.po.task.TaskPO;
import top.minecode.po.worker.FinishedTaskParticipationPO;
import top.minecode.po.worker.OnGoingTaskParticipationPO;
import top.minecode.po.worker.SubTaskParticipationPO;
import top.minecode.po.worker.WorkerPO;

import java.util.Date;
import java.util.List;

/**
 * Created on 2018/6/4.
 * Description:
 *
 * @author iznauy
 */
@Repository
public class TaskSettlementDao {

    private CommonOperation<SubTaskParticipationPO> subTaskParticipationHelper =
            new CommonOperation<>(SubTaskParticipationPO.class);

    private CommonOperation<TaskPO> taskHelper =
            new CommonOperation<>(TaskPO.class);

    private CommonOperation<SubTaskPO> subTaskHelper =
            new CommonOperation<>(SubTaskPO.class);

    private CommonOperation<OnGoingTaskParticipationPO> onGoingTaskParticipationHelper =
            new CommonOperation<>(OnGoingTaskParticipationPO.class);

    private CommonOperation<FinishedTaskParticipationPO> finishedTaskParticipationHelper =
            new CommonOperation<>(FinishedTaskParticipationPO.class);

    private CommonOperation<WorkerPO> workerHelper =
            new CommonOperation<>(WorkerPO.class);

    public List<TaskPO> getCanSettledTasks() {
        // 提前已经完成的任务或者到期的任务
        String queryFinishedTasks = "select * from " + TaskPO.class.getName() + " t where t.taskState = " + TaskState.ON_GOING
                + " and ((not exists(select * from " + SubTaskPO.class.getName() + " subT where subT.subTaskState <> "
                + SubTaskState.FINISHED + ")) or t.endDate <= " + new Date().toString() +  ")";
        return taskHelper.executeSQL(queryFinishedTasks);
    }

    public List<SubTaskParticipationPO> getParticipationByTaskId(int taskId) {
        return subTaskParticipationHelper.getListBySingleField("taskId", taskId);
    }

    public OnGoingTaskParticipationPO getOnGoingTaskParticipationPOByEmailAndTaskId(String email, int taskId) {
        String hql = "select * from " + OnGoingTaskParticipationPO.class.getName() + " t where t.taskId = " + taskId + " and "
                 + "t.userEmail = " + email;
        return onGoingTaskParticipationHelper.executeHQL(hql);
    }

    public void deleteOngoingPart(OnGoingTaskParticipationPO po) {
        onGoingTaskParticipationHelper.delete(po);
    }

    public void addFinishedPartPO(FinishedTaskParticipationPO finishedTaskParticipationPO) {
        finishedTaskParticipationHelper.add(finishedTaskParticipationPO);
    }

    public void batchUpdateWorkerInfo(List<WorkerPO> workerPOS) {
        workerHelper.batchUpdate(workerPOS);
    }

    public void updateTaskPO(TaskPO taskPO) {
        taskHelper.update(taskPO);
    }

    public void batchUpdateSubPart(List<SubTaskParticipationPO> subTaskParticipationPOS) {
        subTaskParticipationHelper.batchUpdate(subTaskParticipationPOS);
    }

}

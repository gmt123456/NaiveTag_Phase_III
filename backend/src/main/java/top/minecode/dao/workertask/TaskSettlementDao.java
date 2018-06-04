package top.minecode.dao.workertask;

import org.springframework.stereotype.Repository;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.domain.task.*;
import top.minecode.po.task.SubTaskPO;
import top.minecode.po.task.TaskPO;
import top.minecode.po.worker.FinishedTaskParticipationPO;
import top.minecode.po.worker.OnGoingTaskParticipationPO;
import top.minecode.po.worker.SubTaskParticipationPO;
import top.minecode.po.worker.WorkerPO;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        java.sql.Date date = new java.sql.Date(new Date().getTime());
        String queryFinishedTasks = "select t from " + TaskPO.class.getName() + " t where t.taskState = '" + TaskState.ON_GOING.toString()
                + "' and ((not exists(select subT from " + SubTaskPO.class.getName() + " subT where subT.subTaskState <> '"
                + SubTaskParticipationState.FINISHED.toString() + "' and subT.taskId = t.id))) or t.endDate <= '" + date.toString() +  "')";

        return taskHelper.executeSQL(queryFinishedTasks).stream().filter(e -> e.getTaskState() == TaskState.ON_GOING).collect(Collectors.toList());
    }

    public List<SubTaskParticipationPO> getParticipationByTaskId(int taskId) {
        return subTaskParticipationHelper.getListBySingleField("taskId", taskId);
    }

    public OnGoingTaskParticipationPO getOnGoingTaskParticipationPOByEmailAndTaskId(String email, int taskId) {
        String hql = "select t from " + OnGoingTaskParticipationPO.class.getName() + " t where t.taskId = " + taskId + " and "
                 + "t.userEmail = '" + email + "'";
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

    public List<SubTaskParticipationPO> getExpiredSubTaskParticipationPOS() {
        java.sql.Date date = new java.sql.Date(new Date().getTime());
        String sql = "select t from " + SubTaskParticipationPO.class.getName() + " t where t.commitDate is null and t.expiredDate < '"
                + date.toString() + "'";
        return subTaskParticipationHelper.executeSQL(sql);
    }

    public void batchUpdateSubTasks(List<SubTaskPO> subTaskPOS) {
        subTaskHelper.batchUpdate(subTaskPOS);
    }

    public List<SubTaskPO> getSubTasksByIds(List<Integer> ids) {
        return subTaskHelper.getValuesInSpecificSet(ids, "id");
    }

}

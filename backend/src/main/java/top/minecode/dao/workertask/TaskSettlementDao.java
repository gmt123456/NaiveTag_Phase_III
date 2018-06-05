package top.minecode.dao.workertask;

import org.springframework.stereotype.Repository;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.domain.task.SubTaskState;
import top.minecode.domain.task.Task;
import top.minecode.domain.task.TaskState;
import top.minecode.po.task.SubTaskPO;
import top.minecode.po.task.TaskPO;
import top.minecode.po.worker.SubTaskParticipationPO;

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


}

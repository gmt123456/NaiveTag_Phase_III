package top.minecode.dao.requester.task;

import top.minecode.domain.task.TaskState;
import top.minecode.domain.task.requester.RequesterTaskItem;
import top.minecode.domain.task.requester.RequesterTaskDetails;
import top.minecode.domain.task.requester.TaskParticipant;

import java.util.List;

/**
 * Created on 2018/5/24.
 * Description:
 * @author Liao
 */
public interface RequesterTaskDao {

    /**
     * Get task information objects for requester's task list
     * @param email requester's email
     * @param state task's state
     * @see TaskState
     * @return list of task items
     */
    List<RequesterTaskItem> getTaskItems(String email, TaskState state);

    /**
     * Get all participants of this task
     * @param taskId task's id
     * @return list of participants
     */
    List<TaskParticipant> getTaskParticipants(int taskId);

    RequesterTaskDetails getTaskDetails(int taskId);
}

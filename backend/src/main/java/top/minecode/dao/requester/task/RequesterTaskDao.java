package top.minecode.dao.requester.task;

import top.minecode.domain.task.TaskState;
import top.minecode.domain.task.requester.TaskItem;
import top.minecode.domain.task.requester.TaskParticipant;

import java.util.List;

/**
 * Created on 2018/5/24.
 * Description:
 * @author Liao
 */
public interface RequesterTaskDao {

    List<TaskItem> getTaskItems(String email, TaskState state);

    List<TaskParticipant> getTaskParticipants(String taskId);
}

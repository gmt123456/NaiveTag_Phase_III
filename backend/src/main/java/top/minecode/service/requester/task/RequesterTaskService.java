package top.minecode.service.requester.task;

import top.minecode.domain.task.requester.TaskItem;
import top.minecode.domain.task.requester.TaskParticipant;

import java.util.List;

/**
 * Created on 2018/5/24.
 * Description:
 * @author Liao
 */
public interface RequesterTaskService {

    List<TaskItem> getOnGoingTasks(String email);

    List<TaskItem> getCompletedTasks(String email);

    List<TaskParticipant> getParticipants(String taskId);
}

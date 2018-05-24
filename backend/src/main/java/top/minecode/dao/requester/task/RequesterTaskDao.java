package top.minecode.dao.requester.task;

import top.minecode.domain.task.TaskState;
import top.minecode.domain.task.requester.TaskItem;

import java.util.List;

/**
 * Created on 2018/5/24.
 * Description:
 * @author Liao
 */
public interface RequesterTaskDao {

    List<TaskItem> getTaskItems(String email, TaskState state);
}

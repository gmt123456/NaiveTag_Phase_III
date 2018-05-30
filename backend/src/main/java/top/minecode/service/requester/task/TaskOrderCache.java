package top.minecode.service.requester.task;

import top.minecode.domain.task.requester.TaskOrder;

/**
 * Created on 2018/5/27.
 * Description:
 * @author Liao
 */
public interface TaskOrderCache {

    String addTaskOrder(TaskOrder order);

    TaskOrder getOrder(String token);
}

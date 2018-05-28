package top.minecode.service.requester.task;

import top.minecode.web.requester.task.NewTaskCommand;
import top.minecode.domain.task.requester.TaskCreationOptions;
import top.minecode.domain.utils.ResultMessage;
import top.minecode.web.requester.task.PayCommand;

/**
 * Created on 2018/5/26.
 * Description:
 * @author Liao
 */
public interface RequesterNewTaskService {

    TaskCreationOptions getCreationInfo();

    ResultMessage createTask(NewTaskCommand newTaskCommand, String email);

    ResultMessage pay(PayCommand payCommand);
}

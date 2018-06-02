package top.minecode.service.staff;

import org.springframework.stereotype.Service;
import top.minecode.domain.task.JoinTaskResponse;
import top.minecode.domain.task.Task;
import top.minecode.domain.taskcheck.TaskCheckSpecification;

import java.util.List;

/**
 * Created on 2018/6/2.
 * Description:
 *
 * @author iznauy
 */
@Service
public class StaffTaskCheckService {

    public List<Task> getAllUnfinishedTasks() {
        return null;
    }

    public TaskCheckSpecification getTaskSpecification(String email, int taskId) {
        return null;
    }

    public JoinTaskResponse joinTask(String email, int taskId) {
        return null;
    }

}

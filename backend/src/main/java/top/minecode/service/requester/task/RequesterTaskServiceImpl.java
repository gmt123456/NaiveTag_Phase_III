package top.minecode.service.requester.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.minecode.dao.requester.task.RequesterTaskDao;
import top.minecode.domain.task.TaskState;
import top.minecode.domain.task.requester.TaskItem;

import java.util.List;

/**
 * Created on 2018/5/24.
 * Description:
 * @author Liao
 */
@Service("requesterTaskServiceImpl")
public class RequesterTaskServiceImpl implements RequesterTaskService {

    private RequesterTaskDao taskDao;

    @Autowired
    public void setTaskDao(RequesterTaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public List<TaskItem> getOnGoingTasks(String email) {
        return taskDao.getTaskItems(email, TaskState.ON_GOING);
    }

    @Override
    public List<TaskItem> getCompletedTasks(String email) {
        return taskDao.getTaskItems(email, TaskState.FINISHED);
    }
}

package top.minecode.service.requester.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.minecode.dao.requester.task.RequesterTaskDao;
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
@Service("requesterTaskServiceImpl")
public class RequesterTaskServiceImpl implements RequesterTaskService {

    private RequesterTaskDao taskDao;

    @Autowired
    public void setTaskDao(RequesterTaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public List<RequesterTaskItem> getOnGoingTasks(String email) {
        return taskDao.getTaskItems(email, TaskState.ON_GOING);
    }

    @Override
    public List<TaskParticipant> getParticipants(int taskId, int limit) {
        List<TaskParticipant> participants = taskDao.getTaskParticipants(taskId);
        if (participants.isEmpty())
            return participants;

        return participants.subList(0, Math.min(limit, participants.size()));
    }

    @Override
    public RequesterTaskDetails getTaskDetails(int taskId) {
        return taskDao.getTaskDetails(taskId);
    }

    @Override
    public List<RequesterTaskItem> getCompletedTasks(String email) {
        return taskDao.getTaskItems(email, TaskState.FINISHED);
    }
}

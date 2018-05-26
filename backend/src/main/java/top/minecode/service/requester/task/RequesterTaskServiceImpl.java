package top.minecode.service.requester.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import top.minecode.dao.requester.task.RequesterTaskDao;
import top.minecode.domain.task.TaskState;
import top.minecode.domain.task.requester.RequesterSubTaskItem;
import top.minecode.domain.task.requester.RequesterTaskDetails;
import top.minecode.domain.task.requester.RequesterTaskItem;
import top.minecode.domain.task.requester.TaskParticipant;
import top.minecode.domain.utils.ResultMessage;
import top.minecode.service.util.PathUtil;

import java.util.List;

/**
 * Created on 2018/5/24.
 * Description:
 * @author Liao
 */
@Service("requesterTaskServiceImpl")
public class RequesterTaskServiceImpl implements RequesterTaskService {

    private static Logger log = LoggerFactory.getLogger(RequesterTaskServiceImpl.class);

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
    public String getReadme(int taskId) {
        return taskDao.getReadme(taskId);
    }

    @Override
    public ResultMessage editReadme(int taskId, String content) {
        if (taskDao.updateReadme(taskId, content))
            return ResultMessage.success();

        return ResultMessage.failure("Whatever");
    }

    @Override
    public String getResultFile(int taskId) {
        String resultFilePath = taskDao.getResultFilePath(taskId);
        // Check whether the task is already over
        Resource resource = new FileSystemResource(PathUtil.getBasePath() + "/" + resultFilePath);
        if (!resource.exists()) {
            log.error("Result file not found");
            return null;
        }

        return resultFilePath;
    }

    @Override
    public List<RequesterSubTaskItem> getSubTasksInfo(int taskId) {
        return taskDao.getSubTaskItem(taskId);
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

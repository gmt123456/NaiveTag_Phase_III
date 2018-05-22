package top.minecode.service.tag;

import javafx.embed.swt.SWTFXUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.minecode.dao.tag.TagDao;
import top.minecode.dao.workertask.SpecificTaskDao;
import top.minecode.dao.workertask.SubTaskDao;
import top.minecode.dao.workertask.TaskDao;
import top.minecode.domain.tag.SwitchPicResponse;
import top.minecode.domain.tag.TagResult;
import top.minecode.domain.task.TaskInfo;
import top.minecode.domain.task.TaskType;
import top.minecode.po.task.SpecificTaskPO;
import top.minecode.po.task.SubTaskPO;
import top.minecode.po.task.TaskPO;

import java.util.List;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
@Service
public class TagService {

    private TagDao tagDao;

    private SubTaskDao subTaskDao;

    private TaskDao taskDao;

    private SpecificTaskDao specificTaskDao;

    public SpecificTaskDao getSpecificTaskDao() {
        return specificTaskDao;
    }

    @Autowired
    public void setSpecificTaskDao(SpecificTaskDao specificTaskDao) {
        this.specificTaskDao = specificTaskDao;
    }

    public TaskDao getTaskDao() {
        return taskDao;
    }

    @Autowired
    public void setTaskDao(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public TagDao getTagDao() {
        return tagDao;
    }

    @Autowired
    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    public SubTaskDao getSubTaskDao() {
        return subTaskDao;
    }

    @Autowired
    public void setSubTaskDao(SubTaskDao subTaskDao) {
        this.subTaskDao = subTaskDao;
    }

    public void save(String userEmail, int taskId, int subTaskId, TaskType taskType, String url, TagResult tagResult) {
        tagDao.saveTagResult(userEmail, taskId, subTaskId, url, tagResult);
    }

    public SwitchPicResponse next(int taskId, int subTaskId, TaskType taskType, String url) { // 这儿的taskId, taskType是不需要的，但是不排除未来不需要
        SubTaskPO subTask = subTaskDao.getSubTaskById(subTaskId);
        List<String> picList = subTask.getPicList();
        int index = 0;
        for (; index < picList.size(); index++)
            if (picList.get(index).equals(url)) {
                break;
            }
        if (index == picList.size())
            return new SwitchPicResponse(null, SwitchPicResponse.INVALID);
        else if (index == picList.size() - 1)
            return new SwitchPicResponse(null, SwitchPicResponse.NO_MORE);
        else
            return new SwitchPicResponse(picList.get(index + 1), null);
    }

    public SwitchPicResponse previous(int taskId, int subTaskId, TaskType taskType, String url) {
        SubTaskPO subTask = subTaskDao.getSubTaskById(subTaskId);
        List<String> picList = subTask.getPicList();
        int index = 0;
        for (; index < picList.size(); index++)
            if (picList.get(index).equals(url)) {
                break;
            }
        if (index == picList.size())
            return new SwitchPicResponse(null, SwitchPicResponse.INVALID);
        else if (index == 0)
            return new SwitchPicResponse(null, SwitchPicResponse.NO_MORE);
        else
            return new SwitchPicResponse(picList.get(index - 1), null);
    }

    public TagResult getLabelInformation(String userEmail, int taskId, int subTaskId, TaskType taskType, String url) {
        return tagDao.getTagResult(userEmail, taskId, subTaskId, url);
    }

    public TaskInfo getTaskInformation(int taskId, TaskType taskType) {
        TaskPO taskPO = taskDao.getTaskById(taskId);
        Integer specificTaskId = taskPO.getSpecificTasks().get(taskType);
        if (specificTaskId == null)
            return null;
        SpecificTaskPO specificTaskPO = specificTaskDao.getSpecificTaskById(specificTaskId);
        if (specificTaskPO == null)
            return null;
        return new TaskInfo(taskType, specificTaskPO
                .getTaskDescription(), specificTaskPO.getLabels());
    }
}

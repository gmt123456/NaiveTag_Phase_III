package top.minecode.service.tag;

import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.minecode.dao.utils.DaoConfig;
import top.minecode.dao.workertask.SpecificTaskDao;
import top.minecode.dao.workertask.SubTaskDao;
import top.minecode.dao.workertask.TaskDao;
import top.minecode.dao.workertask.TaskParticipationDao;
import top.minecode.domain.tag.SwitchPicResponse;
import top.minecode.domain.tag.TagResult;
import top.minecode.domain.task.TaskInfo;
import top.minecode.domain.task.TaskType;
import top.minecode.po.task.SpecificTaskPO;
import top.minecode.po.task.SubTaskPO;
import top.minecode.po.task.TaskPO;
import top.minecode.po.worker.SubTaskParticipationPO;
import top.minecode.service.util.HttpHelper;
import top.minecode.service.util.PathUtil;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
@Service
public class TagService {

    private TaskParticipationDao participationDao;

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

    public TaskParticipationDao getParticipationDao() {
        return participationDao;
    }

    @Autowired
    public void setParticipationDao(TaskParticipationDao participationDao) {
        this.participationDao = participationDao;
    }

    public SubTaskDao getSubTaskDao() {
        return subTaskDao;
    }

    @Autowired
    public void setSubTaskDao(SubTaskDao subTaskDao) {
        this.subTaskDao = subTaskDao;
    }

    public void save(String userEmail, int taskId, int subTaskId, TaskType taskType, String url, TagResult tagResult) {
        SubTaskParticipationPO participationPO =
                participationDao.getWorkerSubTaskParticipation(userEmail, taskId, subTaskId);
        if (participationPO != null) {
            participationPO.getTags().put(url, DaoConfig.getGson().toJson(tagResult, TagResult.class));
            participationDao.updateSubTaskParticipation(participationPO);
        }
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
        SubTaskParticipationPO participationPO =
                participationDao.getWorkerSubTaskParticipation(userEmail, taskId, subTaskId);
        if (participationPO != null) {
            Map<String, String> url2Tag = participationPO.getTags();
            if (!url2Tag.keySet().contains(url))
                return null;
            else {
                return DaoConfig.getGson().fromJson(url2Tag.get(url), TagResult.class);
            }
        }
        return null;
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

    public String getRecommendationLabels(int taskId, String url) {
        Map<String, String> params = new HashMap<>();
        params.put("task_id", String.valueOf(taskId));
        params.put("pic_path", PathUtil.convertToAbsolutePath(url));
        String serverUrl = PathUtil.getPythonServerPath();
        String param = HttpHelper.urlEncode(params);
        return HttpHelper.send(serverUrl, param);
    }
}

package top.minecode.service.tag;

import org.springframework.stereotype.Service;
import top.minecode.domain.tag.TagResult;
import top.minecode.domain.task.TaskInfo;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
@Service
public class TagService {

    public void save(String userEmail, int taskId, int subTaskId, int taskType, String url, TagResult tagResult) {

    }

    public String next(int taskId, int subTaskId, int taskType, String url) {
        return null;
    }

    public String previous(int taskId, int subTaskId, int taskType, String url) {
        return null;
    }

    public String getLabelInformation(String userEmail, int taskId, int subTaskId, int taskType, String url) {
        return null;
    }

    public TaskInfo getTaskInformation(int taskId, int subTaskId) {
        return null;
    }
}

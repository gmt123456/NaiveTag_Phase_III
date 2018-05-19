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
@Service("tagService")
public class TagServiceImpl implements TagService {

    @Override
    public void save(String userEmail, int taskId, int subTaskId, int taskType, String url, TagResult tagResult) {

    }

    @Override
    public String next(int taskId, int subTaskId, int taskType, String url) {
        return null;
    }

    @Override
    public String previous(int taskId, int subTaskId, int taskType, String url) {
        return null;
    }

    @Override
    public String getLabelInformation(String userEmail, int taskId, int subTaskId, int taskType, String url) {
        return null;
    }

    @Override
    public TaskInfo getTaskInformation(int taskId, int subTaskId) {
        return null;
    }
}

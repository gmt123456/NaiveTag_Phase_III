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
public interface TagService {

    void save(String userEmail, int taskId, int subTaskId, int taskType, String url, TagResult tagResult);

    String next(int taskId, int subTaskId, int taskType, String url);

    String previous(int taskId, int subTaskId, int taskType, String url);

    String getLabelInformation(String userEmail, int taskId, int subTaskId, int taskType, String url);

    TaskInfo getTaskInformation(int taskId, int subTaskId);

}

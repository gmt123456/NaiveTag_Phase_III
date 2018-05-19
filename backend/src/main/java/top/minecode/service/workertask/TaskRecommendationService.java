package top.minecode.service.workertask;

import org.springframework.stereotype.Service;
import top.minecode.domain.task.RankType;
import top.minecode.domain.task.Task;
import top.minecode.domain.task.TaskType;

import java.util.List;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
@Service
public class TaskRecommendationService {

    public List<Task> getTaskRecommendations(String email, TaskType taskType,
                                             String taskTag, RankType rankType, int begin, int step) {
        return null;
    }

    private List<Task> getTaskRecommendations(String email, TaskType taskType, String taskTag, RankType rankType) {
        return null;
    }
}

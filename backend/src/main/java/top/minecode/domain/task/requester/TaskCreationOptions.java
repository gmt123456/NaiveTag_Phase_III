package top.minecode.domain.task.requester;

import top.minecode.domain.task.TaskTag;
import top.minecode.domain.user.worker.Division;

import java.util.Arrays;

/**
 * Created on 2018/5/26.
 * Description:
 * @author Liao
 */
public class TaskCreationOptions {

    private TaskTag[] tags;
    private Division[] divisions = Division.values();

    public TaskCreationOptions() {
        TaskTag[] taskTags = TaskTag.values();
        tags = Arrays.copyOf(taskTags, taskTags.length - 1);
    }

    @Override
    public String toString() {
        return "TaskCreationOptions{" +
                "tags=" + Arrays.toString(tags) +
                ", divisions=" + Arrays.toString(divisions) +
                '}';
    }
}

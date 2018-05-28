package top.minecode.domain.task.requester;

import top.minecode.domain.task.Task;
import top.minecode.domain.task.TaskTag;
import top.minecode.domain.user.worker.Division;

import java.util.Arrays;
import java.util.List;

/**
 * Created on 2018/5/26.
 * Description:
 * @author Liao
 */
public class TaskCreationOptions {

    private TaskTag[] tags;
    private Division[] divisions = Division.values();
    private List<Integer> labelTasks;
    private List<Integer> describeTasks;

    public TaskCreationOptions() {
        TaskTag[] taskTags = TaskTag.values();
        tags = Arrays.copyOf(taskTags, taskTags.length - 1);
    }

    public List<Integer> getLabelTasks() {
        return labelTasks;
    }

    public void setLabelTasks(List<Integer> labelTasks) {
        this.labelTasks = labelTasks;
    }

    public List<Integer> getDescribeTasks() {
        return describeTasks;
    }

    public void setDescribeTasks(List<Integer> describeTasks) {
        this.describeTasks = describeTasks;
    }

    @Override
    public String toString() {
        return "TaskCreationOptions{" +
                "tags=" + Arrays.toString(tags) +
                ", divisions=" + Arrays.toString(divisions) +
                ", labelTasks=" + labelTasks +
                ", describeTasks=" + describeTasks +
                '}';
    }
}

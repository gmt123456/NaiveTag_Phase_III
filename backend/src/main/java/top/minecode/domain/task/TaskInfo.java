package top.minecode.domain.task;

import java.util.List;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
public class TaskInfo {

    private TaskType taskType;

    private String description;

    private List<String> classes;

    public TaskInfo(TaskType taskType, String description, List<String> classes) {
        this.taskType = taskType;
        this.description = description;
        this.classes = classes;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getClasses() {
        return classes;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }

    @Override
    public String toString() {
        return "TaskInfo{" +
                "taskType=" + taskType +
                ", description='" + description + '\'' +
                ", classes=" + classes +
                '}';
    }
}

package top.minecode.domain.task.requester;

import top.minecode.domain.task.TaskType;

import java.util.List;

/**
 * Created on 2018/5/24.
 * Description:
 * @author Liao
 */
public class TaskDetails {

    private TaskItem basicInfo;
    private List<TaskType> types;
    private double process;

    public TaskDetails(TaskItem basicInfo, List<TaskType> types, double process) {
        this.basicInfo = basicInfo;
        this.types = types;
        this.process = process;
    }

    public TaskItem getBasicInfo() {
        return basicInfo;
    }

    public List<TaskType> getTypes() {
        return types;
    }

    public double getProcess() {
        return process;
    }
}

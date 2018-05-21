package top.minecode.domain.task;

import top.minecode.po.worker.SubTaskParticipationPO;

/**
 * Created on 2018/5/21.
 * Description:
 *
 * @author iznauy
 */
public class SubTaskDetail {

    private int taskId;

    private int subTaskId;

    private SubTaskParticipationState taskState;

    private String taskName;

    private TaskType taskType;

    private String taskDescription;

    public SubTaskDetail() {
    }

    public SubTaskDetail(int taskId, int subTaskId,
                         SubTaskParticipationState taskState, String taskName,
                         TaskType taskType, String taskDescription) {
        this.taskId = taskId;
        this.subTaskId = subTaskId;
        this.taskState = taskState;
        this.taskName = taskName;
        this.taskType = taskType;
        this.taskDescription = taskDescription;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getSubTaskId() {
        return subTaskId;
    }

    public void setSubTaskId(int subTaskId) {
        this.subTaskId = subTaskId;
    }

    public void setTaskState(SubTaskParticipationState taskState) {
        this.taskState = taskState;
    }

    public SubTaskParticipationState getTaskState() {
        return taskState;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
}

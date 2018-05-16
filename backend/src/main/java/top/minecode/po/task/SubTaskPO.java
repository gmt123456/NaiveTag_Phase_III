package top.minecode.po.task;

import top.minecode.domain.task.SubTaskState;
import top.minecode.domain.task.TaskType;

import java.util.List;

/**
 * Created on 2018/5/16.
 * Description:
 *
 * @author iznauy
 */
public class SubTaskPO {

    private int id;

    private TaskType taskType;

    private String taskDescription;

    private List<String> picList;

    private List<Integer> currentDoingWorkerIds;

    private SubTaskState subTaskState;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<String> getPicList() {
        return picList;
    }

    public void setPicList(List<String> picList) {
        this.picList = picList;
    }

    public List<Integer> getCurrentDoingWorkerIds() {
        return currentDoingWorkerIds;
    }

    public void setCurrentDoingWorkerIds(List<Integer> currentDoingWorkerIds) {
        this.currentDoingWorkerIds = currentDoingWorkerIds;
    }

    public SubTaskState getSubTaskState() {
        return subTaskState;
    }

    public void setSubTaskState(SubTaskState subTaskState) {
        this.subTaskState = subTaskState;
    }
}

package top.minecode.po.task;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import top.minecode.domain.task.SubTaskState;
import top.minecode.domain.task.TaskType;

import javax.persistence.*;
import java.util.List;

/**
 * Created on 2018/5/16.
 * Description:
 *
 * @author iznauy
 */
@Entity
public class SubTaskPO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private TaskType taskType;

    private String taskDescription;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = String.class)
    @Fetch(FetchMode.SUBSELECT)
    private List<String> picList;

    private SubTaskState subTaskState;

    private String currentDoingWorker;

    private String cover;

    private int subTaskId;

    private int taskId;

    public SubTaskPO() {}

    public SubTaskPO(TaskType taskType, String taskDescription,
                     List<String> picList, int subTaskId, int taskId) {
        this.taskType = taskType;
        this.taskDescription = taskDescription;
        this.picList = picList;
        this.subTaskId = subTaskId;
        this.taskId = taskId;

        subTaskState = SubTaskState.COMMON;
        cover = picList.get(0);  // Using first picture as the cover
    }

    public int getSubTaskId() {
        return subTaskId;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

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

    public SubTaskState getSubTaskState() {
        return subTaskState;
    }

    public void setSubTaskState(SubTaskState subTaskState) {
        this.subTaskState = subTaskState;
    }

    public String getCurrentDoingWorker() {
        return currentDoingWorker;
    }

    public void setCurrentDoingWorker(String currentDoingWorker) {
        this.currentDoingWorker = currentDoingWorker;
    }

}

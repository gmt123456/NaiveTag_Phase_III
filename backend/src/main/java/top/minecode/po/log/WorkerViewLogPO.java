package top.minecode.po.log;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import top.minecode.domain.task.TaskTag;
import top.minecode.domain.task.TaskType;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created on 2018/5/17.
 * Description:
 *
 * @author iznauy
 */
@Entity
public class WorkerViewLogPO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String userEmail;

    private int taskId;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = TaskTag.class)
    @Fetch(FetchMode.SUBSELECT)
    private List<TaskTag> taskTags;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = TaskType.class)
    @Fetch(FetchMode.SUBSELECT)
    private List<TaskType> taskTypes;

    private double totalDollars;

    private int picAmount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public List<TaskTag> getTaskTags() {
        return taskTags;
    }

    public void setTaskTags(List<TaskTag> taskTags) {
        this.taskTags = taskTags;
    }

    public List<TaskType> getTaskTypes() {
        return taskTypes;
    }

    public void setTaskTypes(List<TaskType> taskTypes) {
        this.taskTypes = taskTypes;
    }

    public double getTotalDollars() {
        return totalDollars;
    }

    public void setTotalDollars(double totalDollars) {
        this.totalDollars = totalDollars;
    }

    public int getPicAmount() {
        return picAmount;
    }

    public void setPicAmount(int picAmount) {
        this.picAmount = picAmount;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}

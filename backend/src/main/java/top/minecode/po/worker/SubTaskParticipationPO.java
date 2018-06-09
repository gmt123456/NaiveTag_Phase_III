package top.minecode.po.worker;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import top.minecode.domain.task.SubTaskParticipationState;
import top.minecode.domain.task.TaskType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Created on 2018/5/17.
 * Description:
 *
 * @author iznauy
 */
@Entity
public class SubTaskParticipationPO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int taskId;

    private int subTaskId;

    private String email;

    @Enumerated(EnumType.STRING)
    private TaskType subTaskType;

    private String cover;

    private int picAmount;

    private double errorRate = 0.0; // 只有评价过了才有用，当这个任务是客服标注的，那么就直接设置为0

    private boolean accept; // 是否接受，如果接受了，但是挣钱是0，那么就是客服标注的

    private boolean evaluated = false; // 参与是否已经评价过

    private double earnedDollars = 0.0; // 挣得的钱

    @ElementCollection(fetch = FetchType.EAGER, targetClass = String.class)
    @Fetch(FetchMode.SUBSELECT)
    private Map<String, String> tags;

    private SubTaskParticipationState state;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expiredDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date commitDate;

    public Date getCommitDate() {
        return commitDate;
    }

    public void setCommitDate(Date commitDate) {
        this.commitDate = commitDate;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getPicAmount() {
        return picAmount;
    }

    public void setPicAmount(int picAmount) {
        this.picAmount = picAmount;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public SubTaskParticipationState getState() {
        return state;
    }

    public void setState(SubTaskParticipationState state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public TaskType getSubTaskType() {
        return subTaskType;
    }

    public void setSubTaskType(TaskType subTaskType) {
        this.subTaskType = subTaskType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEvaluated() {
        return evaluated;
    }

    public void setEvaluated(boolean evaluated) {
        this.evaluated = evaluated;
    }

    public double getErrorRate() {
        return errorRate;
    }

    public void setErrorRate(double errorRate) {
        this.errorRate = errorRate;
    }

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    public double getEarnedDollars() {
        return earnedDollars;
    }

    public void setEarnedDollars(double earnedDollars) {
        this.earnedDollars = earnedDollars;
    }

    @Override
    public String toString() {
        return "SubTaskParticipationPO{" +
                "id=" + id +
                ", taskId=" + taskId +
                ", subTaskId=" + subTaskId +
                ", email='" + email + '\'' +
                ", subTaskType=" + subTaskType +
                ", cover='" + cover + '\'' +
                ", picAmount=" + picAmount +
                ", errorRate=" + errorRate +
                ", accept=" + accept +
                ", evaluated=" + evaluated +
                ", earnedDollars=" + earnedDollars +
                ", tags=" + tags +
                ", state=" + state +
                ", expiredDate=" + expiredDate +
                ", commitDate=" + commitDate +
                '}';
    }
}

package top.minecode.po.task;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import top.minecode.domain.task.TaskRequirement;
import top.minecode.domain.task.TaskType;
import top.minecode.domain.taskcheck.SubCheckTaskState;
import top.minecode.po.worker.SubTaskParticipationPO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2018/6/2.
 * Description:
 *
 * @author iznauy
 */
@Entity
public class SubCheckTaskPO {

    @Id
    private int subPartId;

    private int taskId;

    private int subTaskId;

    @Enumerated(EnumType.STRING)
    private TaskType subTaskType;

    private String cover;

    private int picAmount;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = String.class)
    @Fetch(FetchMode.SUBSELECT)
    private Map<String, String> sampledTags;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Boolean.class)
    @Fetch(FetchMode.SUBSELECT)
    private Map<String, Boolean> picAccept;

    @Enumerated(EnumType.STRING)
    private SubCheckTaskState checkTaskState;

    private String acceptorEmail;

    private double acceptRate;

    public SubCheckTaskPO(){

    }

    public SubCheckTaskPO(SubTaskParticipationPO participationPO, TaskRequirement requirement) {
        this.subPartId = participationPO.getId();
        this.taskId = participationPO.getTaskId();
        this.subTaskId = participationPO.getSubTaskId();
        this.subTaskType = participationPO.getSubTaskType();
        this.cover = participationPO.getCover();
        this.picAmount = (int) Math.ceil(participationPO.getPicAmount() * 0.3);
        this.sampledTags = new HashMap<>();
        this.picAccept = new HashMap<>();
        this.checkTaskState = SubCheckTaskState.unaccepted;
        if (requirement == TaskRequirement.SPEED) {
            this.acceptRate = 0.6;
        } else if (requirement == TaskRequirement.COMMON) {
            this.acceptRate = 0.8;
        } else {
            this.acceptRate = 0.9;
        }
        List<String> pics = new ArrayList<>(participationPO.getTags().keySet());
        for (int i = 0; i < picAmount; i++) {
            sampledTags.put(pics.get(i), participationPO.getTags().get(pics.get(i)));
        }
    }

    public double getAcceptRate() {
        return acceptRate;
    }

    public void setAcceptRate(double acceptRate) {
        this.acceptRate = acceptRate;
    }

    public int getSubPartId() {
        return subPartId;
    }

    public void setSubPartId(int subPartId) {
        this.subPartId = subPartId;
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

    public Map<String, String> getSampledTags() {
        return sampledTags;
    }

    public void setSampledTags(Map<String, String> sampledTags) {
        this.sampledTags = sampledTags;
    }

    public Map<String, Boolean> getPicAccept() {
        return picAccept;
    }

    public void setPicAccept(Map<String, Boolean> picAccept) {
        this.picAccept = picAccept;
    }

    public SubCheckTaskState getCheckTaskState() {
        return checkTaskState;
    }

    public void setCheckTaskState(SubCheckTaskState checkTaskState) {
        this.checkTaskState = checkTaskState;
    }

    public String getAcceptorEmail() {
        return acceptorEmail;
    }

    public void setAcceptorEmail(String acceptorEmail) {
        this.acceptorEmail = acceptorEmail;
    }

    @Override
    public String toString() {
        return "SubCheckTaskPO{" +
                "subPartId=" + subPartId +
                ", taskId=" + taskId +
                ", subTaskId=" + subTaskId +
                ", subTaskType=" + subTaskType +
                ", cover='" + cover + '\'' +
                ", picAmount=" + picAmount +
                ", sampledTags=" + sampledTags +
                ", picAccept=" + picAccept +
                ", checkTaskState=" + checkTaskState +
                ", acceptorEmail='" + acceptorEmail + '\'' +
                ", acceptRate=" + acceptRate +
                '}';
    }
}

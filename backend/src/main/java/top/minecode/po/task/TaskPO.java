package top.minecode.po.task;

import top.minecode.domain.task.TaskState;
import top.minecode.domain.task.TaskTag;
import top.minecode.domain.task.TaskType;
import top.minecode.domain.user.worker.Division;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Created on 2018/5/16.
 * Description:
 *
 * @author iznauy
 */
@Entity
public class TaskPO implements Serializable {

    @Id
    private int id;

    private String taskName;

    private String backgroundImage;

    private String readme;

    private String ownerEmail;

    private Division lowestDivision;

    private double totalDollars;

    private Double actualDollars; // 没有结算之前就是null

    private double adRate;

    private LocalDate beginDate;

    private LocalDate endDate;

    private LocalDateTime settleTime;

    private TaskState taskState;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = TaskTag.class)
    private List<TaskTag> taskTags;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Integer.class)
    private Map<TaskType, Integer> specificTasks;

    private String resultFilePath; // nullable

    private int picNum;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Integer.class)
    private List<Integer> participators;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getReadme() {
        return readme;
    }

    public void setReadme(String readme) {
        this.readme = readme;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public Division getLowestDivision() {
        return lowestDivision;
    }

    public void setLowestDivision(Division lowestDivision) {
        this.lowestDivision = lowestDivision;
    }

    public double getTotalDollars() {
        return totalDollars;
    }

    public void setTotalDollars(double totalDollars) {
        this.totalDollars = totalDollars;
    }

    public Double getActualDollars() {
        return actualDollars;
    }

    public void setActualDollars(Double actualDollars) {
        this.actualDollars = actualDollars;
    }

    public double getAdRate() {
        return adRate;
    }

    public void setAdRate(double adRate) {
        this.adRate = adRate;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(LocalDateTime settleTime) {
        this.settleTime = settleTime;
    }

    public TaskState getTaskState() {
        return taskState;
    }

    public void setTaskState(TaskState taskState) {
        this.taskState = taskState;
    }

    public List<TaskTag> getTaskTags() {
        return taskTags;
    }

    public void setTaskTags(List<TaskTag> taskTags) {
        this.taskTags = taskTags;
    }

    public Map<TaskType, Integer> getSpecificTasks() {
        return specificTasks;
    }

    public void setSpecificTasks(Map<TaskType, Integer> specificTasks) {
        this.specificTasks = specificTasks;
    }

    public String getResultFilePath() {
        return resultFilePath;
    }

    public void setResultFilePath(String resultFilePath) {
        this.resultFilePath = resultFilePath;
    }

    public int getPicNum() {
        return picNum;
    }

    public void setPicNum(int picNum) {
        this.picNum = picNum;
    }

    public List<Integer> getParticipators() {
        return participators;
    }

    public void setParticipators(List<Integer> participators) {
        this.participators = participators;
    }
}

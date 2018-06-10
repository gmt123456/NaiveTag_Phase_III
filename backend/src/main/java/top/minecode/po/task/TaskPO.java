package top.minecode.po.task;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import top.minecode.domain.task.TaskRequirement;
import top.minecode.domain.task.TaskState;
import top.minecode.domain.task.TaskTag;
import top.minecode.domain.task.TaskType;
import top.minecode.domain.user.worker.Division;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created on 2018/5/16.
 * Description:
 *
 * @author iznauy
 */
@Entity
public class TaskPO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String taskName;

    private String backgroundImage;

    private String taskDescription;

    private String readme;

    private String ownerEmail;

    private Division lowestDivision;

    private double totalDollars;

    private Double actualDollars; // 没有结算之前就是null

    private double adRate;

    private double prizeRate;  // total dollars / lower bound

    private String cover;

    @Temporal(TemporalType.TIMESTAMP)
    private Date beginDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date settleTime;

    @Enumerated(EnumType.STRING)
    private TaskState taskState;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = TaskTag.class)
    @Fetch(FetchMode.SUBSELECT)
    private List<TaskTag> taskTags;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Integer.class)
    @Fetch(FetchMode.SUBSELECT)
    private Map<TaskType, Integer> specificTasks;

    private String resultFilePath; // nullable

    private int picNum;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = String.class)
    @Fetch(FetchMode.SUBSELECT)
    private List<String> participators;

    @Enumerated(EnumType.STRING)
    private TaskRequirement requirement;

    public TaskRequirement getRequirement() {
        return requirement;
    }

    public void addActualDollars(double dollars) {
        if (this.actualDollars == null)
            actualDollars = 0.0;
        this.actualDollars += dollars;
    }

    public void setRequirement(TaskRequirement requirement) {
        this.requirement = requirement;
    }

    public TaskPO() {}

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

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

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(Date settleTime) {
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

    public List<String> getParticipators() {
        return participators;
    }

    public void setParticipators(List<String> participators) {
        this.participators = participators;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public double getPrizeRate() {
        return prizeRate;
    }

    public void setPrizeRate(double prizeRate) {
        this.prizeRate = prizeRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskPO taskPO = (TaskPO) o;
        return id == taskPO.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TaskPO{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", backgroundImage='" + backgroundImage + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", readme='" + readme + '\'' +
                ", ownerEmail='" + ownerEmail + '\'' +
                ", lowestDivision=" + lowestDivision +
                ", totalDollars=" + totalDollars +
                ", actualDollars=" + actualDollars +
                ", adRate=" + adRate +
                ", prizeRate=" + prizeRate +
                ", cover='" + cover + '\'' +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", settleTime=" + settleTime +
                ", taskState=" + taskState +
                ", taskTags=" + taskTags +
                ", specificTasks=" + specificTasks +
                ", resultFilePath='" + resultFilePath + '\'' +
                ", picNum=" + picNum +
                ", participators=" + participators +
                ", requirement=" + requirement +
                '}';
    }
}

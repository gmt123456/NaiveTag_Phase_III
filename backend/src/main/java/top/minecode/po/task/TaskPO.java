package top.minecode.po.task;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import top.minecode.domain.task.TaskState;
import top.minecode.domain.task.TaskTag;
import top.minecode.domain.task.TaskType;
import top.minecode.domain.user.worker.Division;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
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

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Integer.class)
    @Fetch(FetchMode.SUBSELECT)
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

    public List<Integer> getParticipators() {
        return participators;
    }

    public void setParticipators(List<Integer> participators) {
        this.participators = participators;
    }
}

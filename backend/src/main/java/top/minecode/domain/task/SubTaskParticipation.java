package top.minecode.domain.task;

import top.minecode.po.worker.SubTaskParticipationPO;

import java.util.Date;

/**
 * Created on 2018/5/20.
 * Description:
 *
 * @author iznauy
 */
public class SubTaskParticipation {

    private String cover;

    private Date expiredDate;

    private Date commitDate;

    private int taskId;

    private int subTaskId;

    private TaskType taskType;

    private int process;

    private int picAmount;

    private Double earnedDollars;

    public SubTaskParticipation(String cover, Date expiredDate,
                                Date commitDate, int taskId, int subTaskId,
                                TaskType taskType, int process, int picAmount, Double earnedDollars) {
        this.cover = cover;
        this.expiredDate = expiredDate;
        this.commitDate = commitDate;
        this.taskId = taskId;
        this.subTaskId = subTaskId;
        this.taskType = taskType;
        this.process = process;
        this.picAmount = picAmount;
        this.earnedDollars = earnedDollars;
    }

    public static SubTaskParticipation fromPO(SubTaskParticipationPO po) {
        Double earnedDollars = null;
        if (po.isEvaluated())
            earnedDollars = po.getEarnedDollars();
        int process = (int) (po.getTags().keySet().size() * 1.0 / po.getPicAmount() * 100);
        return new SubTaskParticipation(po.getCover(), po.getExpiredDate(), po.getCommitDate(),
                po.getTaskId(), po.getSubTaskId(), po.getSubTaskType(), process, po.getPicAmount(), earnedDollars);
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Date getCommitDate() {
        return commitDate;
    }

    public void setCommitDate(Date commitDate) {
        this.commitDate = commitDate;
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

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public int getProcess() {
        return process;
    }

    public void setProcess(int process) {
        this.process = process;
    }

    public int getPicAmount() {
        return picAmount;
    }

    public void setPicAmount(int picAmount) {
        this.picAmount = picAmount;
    }

    public Double getEarnedDollars() {
        return earnedDollars;
    }

    public void setEarnedDollars(Double earnedDollars) {
        this.earnedDollars = earnedDollars;
    }
}

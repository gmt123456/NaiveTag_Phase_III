package top.minecode.domain.statistic;

import top.minecode.domain.task.TaskType;
import top.minecode.po.worker.SubTaskParticipationPO;

public class ParticipationData {

    private double errorRate;
    private TaskType taskType;
    private String worker;
    private double earnedDollars;
    private int picAmount;
    private boolean evaluated;
    private int taskId;

    public ParticipationData(SubTaskParticipationPO po, TaskType taskType) {
        this.taskType = taskType;

        errorRate = po.getErrorRate();
        worker = po.getEmail();
        earnedDollars = po.getEarnedDollars();
        picAmount = po.getPicAmount();
        evaluated = po.isEvaluated();
        taskId = po.getTaskId();
    }

    public int getPicAmount() {
        return picAmount;
    }

    public double getErrorRate() {
        return errorRate;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public String getWorker() {
        return worker;
    }

    public double getEarnedDollars() {
        return earnedDollars;
    }

    public boolean isEvaluated() {
        return evaluated;
    }

    public int getTaskId() {
        return taskId;
    }

    @Override
    public String toString() {
        return "ParticipationData{" +
                "errorRate=" + errorRate +
                ", taskType=" + taskType +
                ", worker='" + worker + '\'' +
                ", earnedDollars=" + earnedDollars +
                ", picAmount=" + picAmount +
                ", evaluated=" + evaluated +
                ", taskId=" + taskId +
                '}';
    }
}

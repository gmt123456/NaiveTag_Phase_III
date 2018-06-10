package top.minecode.domain.user.worker;

import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class WorkerCommitmentLog {

    private int number;
    private Date lastCommitTime;
    private int taskId;
    private Date joinTime;

    public WorkerCommitmentLog(int number, Date lastCommitTime, int taskId, Date joinTime) {
        this.number = number;
        this.lastCommitTime = lastCommitTime;
        this.taskId = taskId;
        this.joinTime = joinTime;
    }

    public int getTaskId() {
        return taskId;
    }

    public double getSpeed() {
        long diffInMills = lastCommitTime.getTime() - joinTime.getTime();
        long days = TimeUnit.DAYS.convert(diffInMills, TimeUnit.DAYS);

        return number * 1. / days;
    }

    @Override
    public String toString() {
        return "WorkerCommitmentLog{" +
                "number=" + number +
                ", lastCommitTime=" + lastCommitTime +
                ", taskId=" + taskId +
                ", joinTime=" + joinTime +
                '}';
    }
}

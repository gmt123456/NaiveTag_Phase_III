package top.minecode.domain.statistic;

import top.minecode.domain.utils.DateUtils;
import top.minecode.po.task.TaskPO;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RequesterTaskData {

    private final int taskId;
    private final int participatedPeopleNum;
    private final long costTime;
    private final double tagError;
    private final double adDollars;
    private final double workerGetDollars;
    private final double prizeDollars;
    private final double backDollars;

    public RequesterTaskData(TaskPO taskPO, int participatedPeopleNum, double tagError,
                             double workerGetDollars, double backDollars) {
        taskId = taskPO.getId();
        adDollars = taskPO.getTotalDollars() * taskPO.getAdRate();
        prizeDollars = taskPO.getTotalDollars();

        // Initialize fields can't get directly
        Date now = new Date();
        costTime = DateUtils.getDateDiff(taskPO.getBeginDate(),
                DateUtils.min(now, taskPO.getEndDate(), taskPO.getSettleTime()),
                TimeUnit.DAYS);

        this.participatedPeopleNum = participatedPeopleNum;
        this.tagError = tagError;
        this.workerGetDollars = workerGetDollars;
        this.backDollars = backDollars;
    }

    @Override
    public String toString() {
        return "RequesterTaskData{" +
                "taskId=" + taskId +
                ", participatedPeopleNum=" + participatedPeopleNum +
                ", costTime=" + costTime +
                ", tagError=" + tagError +
                ", adDollars=" + adDollars +
                ", workerGetDollars=" + workerGetDollars +
                ", prizeDollars=" + prizeDollars +
                ", backDollars=" + backDollars +
                '}';
    }
}

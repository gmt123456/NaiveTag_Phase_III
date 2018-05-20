package top.minecode.domain.task;

import top.minecode.po.task.TaskPO;
import top.minecode.po.worker.FinishedTaskParticipationPO;

import java.util.Date;
import java.util.List;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
public class FinishedTask extends Task {

    private double changeOfScore;

    private double earnedDollars;

    public static FinishedTask fromPO(TaskPO po, FinishedTaskParticipationPO participation) {
        Task task = Task.fromPO(po);
        double changeOfScore = participation.getScoreChange();
        double earnedDollars = participation.getEarnedDollars();
        return new FinishedTask(task, changeOfScore, earnedDollars);
    }

    public FinishedTask() {
    }

    private FinishedTask(Task task, double changeOfScore, double earnedDollars) {
        super(task);
        this.changeOfScore = changeOfScore;
        this.earnedDollars = earnedDollars;
    }

    public FinishedTask(String name, int taskId, String taskDescription, String taskCover,
                        List<TaskType> taskTypes, Date endDate, List<TaskTag> taskTags,
                        double totalDollars, double changeOfScore, double earnedDollars) {
        super(name, taskId, taskDescription, taskCover, taskTypes, endDate, taskTags, totalDollars);
        this.changeOfScore = changeOfScore;
        this.earnedDollars = earnedDollars;
    }

    public double getEarnedDollars() {
        return earnedDollars;
    }

    public void setEarnedDollars(double earnedDollars) {
        this.earnedDollars = earnedDollars;
    }

    public double getChangeOfScore() {
        return changeOfScore;
    }

    public void setChangeOfScore(double changeOfScore) {
        this.changeOfScore = changeOfScore;
    }
}

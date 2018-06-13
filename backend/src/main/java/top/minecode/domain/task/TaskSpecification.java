package top.minecode.domain.task;

import top.minecode.domain.user.worker.Division;

import java.util.Date;
import java.util.List;

/**
 * Created on 2018/5/21.
 * Description:
 *
 * @author iznauy
 */
public class TaskSpecification extends Task {

    private TaskState state;

    private boolean participated;

    private Division requiredDivision;

    private String taskBackground;

    private boolean canAccept;

    private double earnedDollars;

    private double scoreChange;

    private String backGroundImage;

    public TaskSpecification() {
    }

    public TaskSpecification(Task task, TaskState state,
                             boolean participated, Division requiredDivision, String taskBackground,
                             boolean canAccept, double earnedDollars, double scoreChange,
                             String backGroundImage) {
        super(task);
        this.state = state;
        this.participated = participated;
        this.requiredDivision = requiredDivision;
        this.taskBackground = taskBackground;
        this.canAccept = canAccept;
        this.earnedDollars = earnedDollars;
        this.backGroundImage = backGroundImage;
        this.scoreChange = scoreChange;
    }

    public TaskSpecification(String name, int taskId, String taskDescription, String taskCover,
                             List<TaskType> taskTypes, Date endDate, List<TaskTag> taskTags, double totalDollars,
                             TaskState state, boolean participated, Division requiredDivision, String taskBackground,
                             boolean canAccept, double earnedDollars, double scoreChange, TaskRequirement requirement) {
        super(name, taskId, taskDescription, taskCover, taskTypes, endDate, taskTags, totalDollars, requirement);
        this.state = state;
        this.participated = participated;
        this.requiredDivision = requiredDivision;
        this.taskBackground = taskBackground;
        this.canAccept = canAccept;
        this.earnedDollars = earnedDollars;
        this.scoreChange = scoreChange;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public boolean isParticipated() {
        return participated;
    }

    public void setParticipated(boolean participated) {
        this.participated = participated;
    }

    public Division getRequiredDivision() {
        return requiredDivision;
    }

    public void setRequiredDivision(Division requiredDivision) {
        this.requiredDivision = requiredDivision;
    }

    public String getTaskBackground() {
        return taskBackground;
    }

    public void setTaskBackground(String taskBackground) {
        this.taskBackground = taskBackground;
    }

    public boolean isCanAccept() {
        return canAccept;
    }

    public void setCanAccept(boolean canAccept) {
        this.canAccept = canAccept;
    }

    public double getEarnedDollars() {
        return earnedDollars;
    }

    public void setEarnedDollars(double earnedDollars) {
        this.earnedDollars = earnedDollars;
    }

    public double getScoreChange() {
        return scoreChange;
    }

    public void setScoreChange(double scoreChange) {
        this.scoreChange = scoreChange;
    }

    public String getBackGroundImage() {
        return backGroundImage;
    }

    public void setBackGroundImage(String backGroundImage) {
        this.backGroundImage = backGroundImage;
    }
}

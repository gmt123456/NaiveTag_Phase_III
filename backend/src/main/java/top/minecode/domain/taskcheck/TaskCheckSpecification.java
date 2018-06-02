package top.minecode.domain.taskcheck;

import top.minecode.domain.task.Task;

/**
 * Created on 2018/6/2.
 * Description:
 *
 * @author iznauy
 */
public class TaskCheckSpecification extends Task {

    private boolean participated;

    private String taskBackground;

    private String backGroundImage;

    public TaskCheckSpecification(Task task, boolean participated, String taskBackground, String
                                  backGroundImage) {
        super(task);
        this.participated = participated;
        this.taskBackground = taskBackground;
        this.backGroundImage = backGroundImage;
    }

    public boolean isParticipated() {
        return participated;
    }

    public void setParticipated(boolean participated) {
        this.participated = participated;
    }

    public String getTaskBackground() {
        return taskBackground;
    }

    public void setTaskBackground(String taskBackground) {
        this.taskBackground = taskBackground;
    }

    public String getBackGroundImage() {
        return backGroundImage;
    }

    public void setBackGroundImage(String backGroundImage) {
        this.backGroundImage = backGroundImage;
    }
}

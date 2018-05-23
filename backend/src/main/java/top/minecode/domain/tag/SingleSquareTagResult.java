package top.minecode.domain.tag;

import top.minecode.domain.task.TaskType;

/**
 * Created on 2018/4/2.
 * Description:
 * @author Liao
 */
public abstract class SingleSquareTagResult extends TagResult {

    private Frame frame;

    public SingleSquareTagResult() {
    }

    public SingleSquareTagResult(TaskType taskType, Frame frame) {
        super(taskType);
        this.frame = frame;
    }

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }
}

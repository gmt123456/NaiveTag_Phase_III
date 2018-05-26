package top.minecode.domain.task.requester;

import top.minecode.domain.task.TaskType;

/**
 * Created on 2018/5/24.
 * Description:
 * @author Liao
 */
public class RequesterSubTaskItem {

    private TaskType type;
    private double process;
    private int participantsNum;

    public RequesterSubTaskItem(TaskType type, double process, int participantsNum) {
        this.type = type;
        this.process = process;
        this.participantsNum = participantsNum;
    }

    public TaskType getType() {
        return type;
    }

    public double getProcess() {
        return process;
    }

    public int getParticipantsNum() {
        return participantsNum;
    }

    @Override
    public String toString() {
        return "RequesterSubTaskItem{" +
                "type=" + type +
                ", process=" + process +
                ", participantsNum=" + participantsNum +
                '}';
    }
}

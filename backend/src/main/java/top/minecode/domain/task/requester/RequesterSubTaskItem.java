package top.minecode.domain.task.requester;

import top.minecode.domain.task.TaskType;

import java.util.List;

/**
 * Created on 2018/5/24.
 * Description:
 * @author Liao
 */
public class RequesterSubTaskItem {

    private TaskType type;
    private double process;
    private int participantsNum;
    private String description;
    private List<TaskParticipant> participants;

    public RequesterSubTaskItem(TaskType type, double process, int participantsNum,
                                List<TaskParticipant> participants, String description) {
        this.type = type;
        this.process = process;
        this.participantsNum = participantsNum;
        this.participants = participants;
        this.description = description;
    }

    @Override
    public String toString() {
        return "RequesterSubTaskItem{" +
                "type=" + type +
                ", process=" + process +
                ", participantsNum=" + participantsNum +
                ", participants=" + participants +
                ", description=" + description +
                '}';
    }
}

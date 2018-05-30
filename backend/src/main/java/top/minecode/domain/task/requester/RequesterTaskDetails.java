package top.minecode.domain.task.requester;

import top.minecode.domain.task.TaskState;
import top.minecode.domain.task.TaskTag;
import top.minecode.domain.task.TaskType;
import top.minecode.domain.user.worker.Division;
import top.minecode.po.task.TaskPO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created on 2018/5/24.
 * Description:
 * @author Liao
 */
public class RequesterTaskDetails {

    private List<TaskType> types;
    private double process;
    private String backgroundImage;
    private String title;
    private String description;
    private List<TaskTag> tags;
    private double dollars;
    private int participantsNum;
    private int pictureNum;
    private Division workerRequirement;
    private Date begin;
    private Date deadline;
    private String cover;
    private TaskState state;


    public RequesterTaskDetails(TaskPO taskPO, double process) {
        backgroundImage = taskPO.getBackgroundImage();
        title = taskPO.getTaskName();
        deadline = taskPO.getEndDate();
        description = taskPO.getTaskDescription();
        tags = taskPO.getTaskTags();
        dollars = taskPO.getTotalDollars();
        participantsNum = taskPO.getParticipators().size();
        pictureNum = taskPO.getPicNum();
        workerRequirement = taskPO.getLowestDivision();
        begin = taskPO.getBeginDate();
        state = taskPO.getTaskState();
        cover = taskPO.getCover();

        types = new ArrayList<>(taskPO.getSpecificTasks().keySet());
        this.process = process;
    }

    public Date getBegin() {
        return begin;
    }

    public TaskState getState() {
        return state;
    }

    public Date getDeadline() {
        return deadline;
    }


    @Override
    public String toString() {
        return "RequesterTaskDetails{" +
                "types=" + types +
                ", process=" + process +
                ", backgroundImage='" + backgroundImage + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", tags=" + tags +
                ", dollars=" + dollars +
                ", participantsNum=" + participantsNum +
                ", pictureNum=" + pictureNum +
                ", workerRequirement=" + workerRequirement +
                ", begin=" + begin +
                ", deadline=" + deadline +
                ", state=" + state +
                ", cover=" + cover +
                '}';
    }
}

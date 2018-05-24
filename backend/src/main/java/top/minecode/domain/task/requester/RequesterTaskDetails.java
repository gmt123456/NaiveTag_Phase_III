package top.minecode.domain.task.requester;

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

        types = new ArrayList<>(taskPO.getSpecificTasks().keySet());
        this.process = process;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<TaskTag> getTags() {
        return tags;
    }

    public double getDollars() {
        return dollars;
    }

    public int getParticipantsNum() {
        return participantsNum;
    }

    public int getPictureNum() {
        return pictureNum;
    }

    public Division getWorkerRequirement() {
        return workerRequirement;
    }

    public Date getBegin() {
        return begin;
    }

    public Date getDeadline() {
        return deadline;
    }


    public List<TaskType> getTypes() {
        return types;
    }

    public double getProcess() {
        return process;
    }
}
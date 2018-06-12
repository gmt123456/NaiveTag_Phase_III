package top.minecode.domain.task.requester;

import top.minecode.domain.task.TaskRequirement;
import top.minecode.domain.task.TaskTag;
import top.minecode.domain.user.worker.Division;
import top.minecode.po.task.TaskPO;

import java.util.Date;
import java.util.List;

/**
 * Created on 2018/5/24.
 * Description:
 * @author Liao
 */
public class RequesterTaskItem {

    private int taskId;
    private String title;
    private String description;
    private String cover;
    private List<TaskTag> tags;
    private double dollars;
    private int participantsNum;
    private int pictureNum;
    private Division workerRequirement;
    private Date begin;
    private Date deadline;
    private TaskRequirement taskRequirement;

    public RequesterTaskItem(TaskPO taskPO) {
        title = taskPO.getTaskName();
        description = taskPO.getTaskDescription();
        cover = taskPO.getCover();
        tags = taskPO.getTaskTags();
        dollars = taskPO.getTotalDollars();
        participantsNum = taskPO.getParticipators().size();
        pictureNum = taskPO.getPicNum();
        begin = taskPO.getBeginDate();
        deadline = taskPO.getEndDate();
        workerRequirement = taskPO.getLowestDivision();
        taskId = taskPO.getId();
        taskRequirement = taskPO.getRequirement();
    }

    public int getTaskId() {
        return taskId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCover() {
        return cover;
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

    public Date getBegin() {
        return begin;
    }

    public Date getDeadline() {
        return deadline;
    }

    public Division getWorkerRequirement() {
        return workerRequirement;
    }

    @Override
    public String toString() {
        return "RequesterTaskItem{" +
                "taskId=" + taskId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", cover='" + cover + '\'' +
                ", tags=" + tags +
                ", dollars=" + dollars +
                ", participantsNum=" + participantsNum +
                ", pictureNum=" + pictureNum +
                ", workerRequirement=" + workerRequirement +
                ", begin=" + begin +
                ", deadline=" + deadline +
                ", taskRequirement=" + taskRequirement +
                '}';
    }
}

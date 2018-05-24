package top.minecode.domain.task.requester;

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
public class TaskItem {

    private String title;
    private String description;
    private String cover;
    private List<TaskTag> tags;
    private double dollars;
    private int participantsNum;
    private int pictureNum;
    private int subTaskNum;
    private Division workerRequirement;
    private Date begin;
    private Date deadline;

    public TaskItem(TaskPO taskPO) {
        title = taskPO.getTaskName();
        description = taskPO.getTaskDescription();
        cover = taskPO.getCover();
        tags = taskPO.getTaskTags();
        dollars = taskPO.getTotalDollars();
        participantsNum = taskPO.getParticipators().size();
        pictureNum = taskPO.getPicNum();
        subTaskNum = taskPO.getSpecificTasks().size();
        begin = taskPO.getBeginDate();
        deadline = taskPO.getEndDate();
        workerRequirement = taskPO.getLowestDivision();
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

    public int getSubTaskNum() {
        return subTaskNum;
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
}

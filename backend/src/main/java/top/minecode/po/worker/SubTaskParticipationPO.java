package top.minecode.po.worker;

import top.minecode.domain.task.SubTaskParticipationState;
import top.minecode.domain.task.TaskType;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Created on 2018/5/17.
 * Description:
 *
 * @author iznauy
 */
public class SubTaskParticipationPO implements Serializable {

    private int id;

    private int taskId;

    private int subTaskId;

    private TaskType subTaskType;

    private int tagResultsId;

    private List<String> unfinishedPic;

    private Map<String, String> tags;

    private SubTaskParticipationState state;

    private LocalDate expiredDate;

    public List<String> getUnfinishedPic() {
        return unfinishedPic;
    }

    public void setUnfinishedPic(List<String> unfinishedPic) {
        this.unfinishedPic = unfinishedPic;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public LocalDate getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(LocalDate expiredDate) {
        this.expiredDate = expiredDate;
    }

    public SubTaskParticipationState getState() {
        return state;
    }

    public void setState(SubTaskParticipationState state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getSubTaskId() {
        return subTaskId;
    }

    public void setSubTaskId(int subTaskId) {
        this.subTaskId = subTaskId;
    }

    public TaskType getSubTaskType() {
        return subTaskType;
    }

    public void setSubTaskType(TaskType subTaskType) {
        this.subTaskType = subTaskType;
    }

    public int getTagResultsId() {
        return tagResultsId;
    }

    public void setTagResultsId(int tagResultsId) {
        this.tagResultsId = tagResultsId;
    }
}

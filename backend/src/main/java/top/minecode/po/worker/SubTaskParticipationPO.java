package top.minecode.po.worker;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import top.minecode.domain.task.SubTaskParticipationState;
import top.minecode.domain.task.TaskType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created on 2018/5/17.
 * Description:
 *
 * @author iznauy
 */
@Entity
public class SubTaskParticipationPO implements Serializable {

    @Id
    private int id;

    private int taskId;

    private int subTaskId;

    @Enumerated(EnumType.STRING)
    private TaskType subTaskType;

    private int tagResultsId;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = String.class)
    @Fetch(FetchMode.SUBSELECT)
    private List<String> unfinishedPic;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = String.class)
    @Fetch(FetchMode.SUBSELECT)
    private Map<String, String> tags;

    private SubTaskParticipationState state;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expiredDate;

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

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
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

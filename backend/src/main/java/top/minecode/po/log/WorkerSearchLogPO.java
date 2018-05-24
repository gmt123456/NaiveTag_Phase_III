package top.minecode.po.log;

import top.minecode.domain.task.RankType;
import top.minecode.domain.task.TaskTag;
import top.minecode.domain.task.TaskType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created on 2018/5/17.
 * Description:
 *
 * @author iznauy
 */
@Entity
public class WorkerSearchLogPO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String searchKey;

    @Enumerated(EnumType.ORDINAL)
    private TaskType taskType;

    @Enumerated(EnumType.ORDINAL)
    private TaskTag taskTag;

    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    private String userEmail;

    @Enumerated(EnumType.ORDINAL)
    private RankType rankType;

    public WorkerSearchLogPO() {
    }

    public WorkerSearchLogPO(String searchKey, TaskType taskType, TaskTag taskTag,
                             Date time, String userEmail, RankType rankType) {
        this.searchKey = searchKey;
        this.taskType = taskType;
        this.taskTag = taskTag;
        this.time = time;
        this.userEmail = userEmail;
        this.rankType = rankType;
    }

    public RankType getRankType() {
        return rankType;
    }

    public void setRankType(RankType rankType) {
        this.rankType = rankType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public TaskTag getTaskTag() {
        return taskTag;
    }

    public void setTaskTag(TaskTag taskTag) {
        this.taskTag = taskTag;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}

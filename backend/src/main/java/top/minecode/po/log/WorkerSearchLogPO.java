package top.minecode.po.log;

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

    private String key;

    @Enumerated(EnumType.STRING)
    private TaskType taskType;

    @Enumerated(EnumType.STRING)
    private TaskTag taskTag;

    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    private String userEmail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

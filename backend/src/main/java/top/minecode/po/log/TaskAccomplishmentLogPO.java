package top.minecode.po.log;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created on 2018/5/17.
 * Description: 这个是一级任务完成log
 *
 * @author iznauy
 */
@Entity
public class TaskAccomplishmentLogPO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int taskId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date accomplishDate;

    public TaskAccomplishmentLogPO() {}

    public TaskAccomplishmentLogPO(int taskId, Date accomplishDate) {
        this.taskId = taskId;
        this.accomplishDate = accomplishDate;
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

    public Date getAccomplishDate() {
        return accomplishDate;
    }

    public void setAccomplishDate(Date accomplishDate) {
        this.accomplishDate = accomplishDate;
    }
}

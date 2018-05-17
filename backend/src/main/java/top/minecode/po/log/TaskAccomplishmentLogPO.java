package top.minecode.po.log;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;

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

    private LocalDate accomplishDate;

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

    public LocalDate getAccomplishDate() {
        return accomplishDate;
    }

    public void setAccomplishDate(LocalDate accomplishDate) {
        this.accomplishDate = accomplishDate;
    }
}

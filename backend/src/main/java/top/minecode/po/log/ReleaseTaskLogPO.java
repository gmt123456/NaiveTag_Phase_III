package top.minecode.po.log;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created on 2018/5/17.
 * Description:
 *
 * @author iznauy
 */
public class ReleaseTaskLogPO implements Serializable {

    private int id;

    private int taskId;

    private LocalDate releaseDate;

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

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}

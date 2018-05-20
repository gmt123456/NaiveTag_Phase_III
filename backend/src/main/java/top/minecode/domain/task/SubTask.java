package top.minecode.domain.task;

/**
 * Created on 2018/5/20.
 * Description:
 *
 * @author iznauy
 */
public class SubTask {

    private int taskId;

    private int subTaskId;

    private int picCount;

    private String cover;

    public SubTask(int taskId, int subTaskId, int picCount, String cover) {
        this.taskId = taskId;
        this.subTaskId = subTaskId;
        this.picCount = picCount;
        this.cover = cover;
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

    public int getPicCount() {
        return picCount;
    }

    public void setPicCount(int picCount) {
        this.picCount = picCount;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}

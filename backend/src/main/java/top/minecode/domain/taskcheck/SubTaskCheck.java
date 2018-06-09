package top.minecode.domain.taskcheck;

import top.minecode.domain.task.TaskType;
import top.minecode.po.task.SubCheckTaskPO;

/**
 * Created on 2018/6/2.
 * Description:
 *
 * @author iznauy
 */
public class SubTaskCheck {

    private int subPartId;

    private int picAmount;

    private String cover;

    private TaskType taskType;

    public static SubTaskCheck fromPO(SubCheckTaskPO po) {
        return new SubTaskCheck(po.getSubPartId(), po.getPicAmount(), po.getCover(), po.getSubTaskType());
    }


    public SubTaskCheck() {
    }

    public SubTaskCheck(int subPartId, int picAmount, String cover, TaskType taskType) {
        this.subPartId = subPartId;
        this.picAmount = picAmount;
        this.cover = cover;
        this.taskType = taskType;
    }

    public int getSubPartId() {
        return subPartId;
    }

    public void setSubPartId(int subPartId) {
        this.subPartId = subPartId;
    }

    public int getPicAmount() {
        return picAmount;
    }

    public void setPicAmount(int picAmount) {
        this.picAmount = picAmount;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}

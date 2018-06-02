package top.minecode.domain.taskcheck;

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

    public static SubTaskCheck fromPO(SubCheckTaskPO po) {
        return new SubTaskCheck(po.getSubPartId(), po.getPicAmount(), po.getCover());
    }


    public SubTaskCheck() {
    }

    public SubTaskCheck(int subPartId, int picAmount, String cover) {
        this.subPartId = subPartId;
        this.picAmount = picAmount;
        this.cover = cover;
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

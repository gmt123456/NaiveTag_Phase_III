package top.minecode.domain.admin;

import org.jetbrains.annotations.NotNull;
import top.minecode.po.requester.RequesterPO;

import java.util.Date;

/**
 * Created on 2018/6/2.
 * Description:
 *
 * @author Liao
 */
public class RequesterItem {

    private String name;
    private String avatar;
    private Date signupTime;
    private double dollars;
    private int tasksNum;
    private String email;

    public RequesterItem(RequesterPO requesterPO, int tasksNum) {
        name = requesterPO.getName();
        avatar = requesterPO.getAvatar();
        dollars = requesterPO.getDollars();
        email = requesterPO.getEmail();
        signupTime = requesterPO.getJoinTime();
        this.tasksNum = tasksNum;
    }

    public String getEmail() {
        return email;
    }

    public void setTasksNum(int tasksNum) {
        this.tasksNum = tasksNum;
    }

    public int getTasksNum() {
        return tasksNum;
    }

    @Override
    public String toString() {
        return "RequesterItem{" +
                "name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", signupTime=" + signupTime +
                ", dollars=" + dollars +
                ", tasksNum=" + tasksNum +
                ", email='" + email + '\'' +
                '}';
    }
}

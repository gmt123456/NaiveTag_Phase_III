package top.minecode.po.worker;

import java.io.Serializable;
import java.util.List;

/**
 * Created on 2018/5/17.
 * Description:
 *
 * @author iznauy
 */
public class FinishedTaskParticipationPO implements Serializable {

    private int id;

    private String userEmail;

    private int taskId;

    private List<SubTaskParticipationPO> participatedSubTaskResults; // 参加的下属任务

    private double earnedDollars;

    private double scoreChange;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public List<SubTaskParticipationPO> getParticipatedSubTaskResults() {
        return participatedSubTaskResults;
    }

    public void setParticipatedSubTaskResults(List<SubTaskParticipationPO> participatedSubTaskResults) {
        this.participatedSubTaskResults = participatedSubTaskResults;
    }

    public double getEarnedDollars() {
        return earnedDollars;
    }

    public void setEarnedDollars(double earnedDollars) {
        this.earnedDollars = earnedDollars;
    }

    public double getScoreChange() {
        return scoreChange;
    }

    public void setScoreChange(double scoreChange) {
        this.scoreChange = scoreChange;
    }
}

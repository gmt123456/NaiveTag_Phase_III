package top.minecode.po.worker;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created on 2018/5/17.
 * Description:
 *
 * @author iznauy
 */
@Entity
public class FinishedTaskParticipationPO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String userEmail;

    private int taskId;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Integer> participatedSubTaskResults; // 参加的下属任务

    private double earnedDollars;

    private double scoreChange; // 范围是 - ？~ + ？，除以100就得到这次任务的质量

    private int acceptedPicAmount;

    public int getAcceptedPicAmount() {
        return acceptedPicAmount;
    }

    public void setAcceptedPicAmount(int picAmount) {
        this.acceptedPicAmount = picAmount;
    }

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

    public List<Integer> getParticipatedSubTaskResults() {
        return participatedSubTaskResults;
    }

    public void setParticipatedSubTaskResults(List<Integer> participatedSubTaskResultIds) {
        this.participatedSubTaskResults = participatedSubTaskResultIds;
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

    public double getStandardScoreChange() {  // 标准分变化，因为每个人标注的数量可能不同，一概而论可能不合适
        return acceptedPicAmount > 0 ? scoreChange  / acceptedPicAmount: 0.0;
    }
}

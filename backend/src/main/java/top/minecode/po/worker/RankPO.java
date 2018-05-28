package top.minecode.po.worker;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created on 2018/5/16.
 * Description:
 *
 * @author iznauy
 */
@Entity
public class RankPO {

    @Id
    private String userEmail;

    private String userName;

    private double score;

    private int rank;

    private String avatar;

    public RankPO() {}

    public RankPO(String userEmail, String userName, double score, String avatar) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.score = score;
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}

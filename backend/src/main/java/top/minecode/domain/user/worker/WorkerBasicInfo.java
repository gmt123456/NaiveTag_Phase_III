package top.minecode.domain.user.worker;

import top.minecode.domain.utils.SignMessageConverter;

import java.util.Date;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
public class WorkerBasicInfo {

    private String avatar;

    private String userName;

    private String email;

    private String lastVisit;

    private int rank;

    private String joint;

    private double dollars;

    private Division division;

    private double score;

    public WorkerBasicInfo(String avatar, String userName,
                           String email, Date lastVisit,
                           int rank, Date joint, double dollars,
                           double score) {
        SignMessageConverter converter = new SignMessageConverter();
        this.avatar = avatar;
        this.userName = userName;
        this.email = email;
        this.lastVisit = converter.convertLogin(lastVisit);
        this.rank = rank;
        this.joint = converter.convertSignUp(joint);
        this.dollars = dollars;
        this.division = Division.convert(score);
        this.score = score;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(String lastVisit) {
        this.lastVisit = lastVisit;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getJoint() {
        return joint;
    }

    public void setJoint(String joint) {
        this.joint = joint;
    }

    public double getDollars() {
        return dollars;
    }

    public void setDollars(double dollars) {
        this.dollars = dollars;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}

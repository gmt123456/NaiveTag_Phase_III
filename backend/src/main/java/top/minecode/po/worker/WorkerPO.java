package top.minecode.po.worker;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created on 2018/5/16.
 * Description:
 *
 * @author iznauy
 */
public class WorkerPO implements Serializable {

    private String email;

    private String password;

    private String name;

    private double Score;

    private double dollar;

    private LocalDateTime joinTime;

    private String avatar;

    private List<Integer> onGoingTaskParticipation;

    private List<Integer> finishedTaskParticipation;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getScore() {
        return Score;
    }

    public void setScore(double score) {
        Score = score;
    }

    public double getDollar() {
        return dollar;
    }

    public void setDollar(double dollar) {
        this.dollar = dollar;
    }

    public LocalDateTime getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(LocalDateTime joinTime) {
        this.joinTime = joinTime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<Integer> getOnGoingTaskParticipation() {
        return onGoingTaskParticipation;
    }

    public void setOnGoingTaskParticipation(List<Integer> onGoingTaskParticipation) {
        this.onGoingTaskParticipation = onGoingTaskParticipation;
    }

    public List<Integer> getFinishedTaskParticipation() {
        return finishedTaskParticipation;
    }

    public void setFinishedTaskParticipation(List<Integer> finishedTaskParticipation) {
        this.finishedTaskParticipation = finishedTaskParticipation;
    }
}

package top.minecode.po.worker;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created on 2018/5/16.
 * Description:
 *
 * @author iznauy
 */
@Entity
public class WorkerPO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String email;

    private String password;

    private String name;

    private double Score;

    private double dollars;

    @Temporal(TemporalType.TIMESTAMP)
    private Date joinTime;

    private String avatar;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Integer.class)
    @Fetch(FetchMode.SUBSELECT)
    private List<Integer> onGoingTaskParticipation;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Integer.class)
    @Fetch(FetchMode.SUBSELECT)
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

    public double getDollars() {
        return dollars;
    }

    public void setDollars(double dollars) {
        this.dollars = dollars;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
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

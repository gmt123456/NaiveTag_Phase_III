package top.minecode.domain.user.worker;

import top.minecode.domain.user.User;
import top.minecode.domain.user.UserType;

import java.util.Date;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
public class Worker extends User {

    private double score;

    public Worker() {
    }

    public Worker(String email, String password,
                  double dollars, Date joinTime, String avatar, double score) {
        super(UserType.WORKER, email, password, dollars, joinTime, avatar);
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}

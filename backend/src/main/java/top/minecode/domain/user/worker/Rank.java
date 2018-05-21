package top.minecode.domain.user.worker;

import org.jetbrains.annotations.NotNull;
import top.minecode.po.worker.RankPO;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
public class Rank implements Comparable<Rank> {

    private String userName;

    private double score;

    private Division division;

    private String avatar;

    public Rank(String userName, double score, Division division, String avatar) {
        this.userName = userName;
        this.score = score;
        this.division = division;
        this.avatar = avatar;
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

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public int compareTo(@NotNull Rank o) {
        return score < o.score ? -1: 1;
    }

    public static Rank convert(RankPO rankPO) {
        return new Rank(rankPO.getUserName(), rankPO.getScore(), Division.convert(rankPO.getScore()), rankPO.getAvatar());
    }

}

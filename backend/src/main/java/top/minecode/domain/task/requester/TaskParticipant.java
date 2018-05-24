package top.minecode.domain.task.requester;

/**
 * Created on 2018/5/24.
 * Description:
 * @author Liao
 */
public class TaskParticipant {

    private String name;
    private String avatar;
    private double score;
    private int pictureNum;

    public TaskParticipant(String name, String avatar, double score, int pictureNum) {
        this.name = name;
        this.avatar = avatar;
        this.score = score;
        this.pictureNum = pictureNum;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    public double getScore() {
        return score;
    }

    public int getPictureNum() {
        return pictureNum;
    }
}

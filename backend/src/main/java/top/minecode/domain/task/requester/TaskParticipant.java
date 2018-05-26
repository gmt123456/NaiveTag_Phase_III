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

    @Override
    public String toString() {
        return "TaskParticipant{" +
                "name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", score=" + score +
                ", pictureNum=" + pictureNum +
                '}';
    }
}

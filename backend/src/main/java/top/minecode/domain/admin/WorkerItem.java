package top.minecode.domain.admin;

import top.minecode.domain.user.worker.Division;
import top.minecode.po.worker.WorkerPO;

import java.util.Date;

/**
 * Created on 2018/6/2.
 * Description:
 * @author Liao
 */
public class WorkerItem {

    private String name;
    private String email;
    private String avatar;
    private Date signupTime;
    private double dollars;
    private Division division;
    private double score;

    public WorkerItem(WorkerPO workerPO) {
        name = workerPO.getName();
        email = workerPO.getEmail();
        avatar = workerPO.getAvatar();
        dollars = workerPO.getDollars();
        signupTime = workerPO.getJoinTime();

        score = workerPO.getScore();
        division = Division.convert(score);
    }

    @Override
    public String toString() {
        return "WorkerItem{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", signupTime=" + signupTime +
                ", dollars=" + dollars +
                ", division=" + division +
                ", score=" + score +
                '}';
    }
}

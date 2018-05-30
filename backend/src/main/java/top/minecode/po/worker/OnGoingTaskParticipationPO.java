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
public class OnGoingTaskParticipationPO implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String userEmail;

    private int taskId;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Integer.class)
    private List<Integer> participatedSubTaskResultIds; // 参加的下属任务

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

    public List<Integer> getParticipatedSubTaskResultIds() {
        return participatedSubTaskResultIds;
    }

    public void setParticipatedSubTaskResultIds(List<Integer> participatedSubTaskResultIds) {
        this.participatedSubTaskResultIds = participatedSubTaskResultIds;
    }
}

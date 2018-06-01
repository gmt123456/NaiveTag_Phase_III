package top.minecode.po.admin;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.List;

/**
 * Created on 2018/6/1.
 * Description:
 *
 * @author iznauy
 */
@Entity
public class StaffPO {

    @Id
    private String email;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Integer.class)
    @Fetch(FetchMode.SUBSELECT)
    private List<Integer> participatedTasks;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Integer.class)
    @Fetch(FetchMode.SUBSELECT)
    private List<Integer> participatedTaskEvaluations;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Integer.class)
    @Fetch(FetchMode.SUBSELECT)
    private List<Integer> onGoingTaskParticipation;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Integer.class)
    @Fetch(FetchMode.SUBSELECT)
    private List<Integer> taskEvaluationsParticipation;

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

    public List<Integer> getParticipatedTasks() {
        return participatedTasks;
    }

    public void setParticipatedTasks(List<Integer> participatedTasks) {
        this.participatedTasks = participatedTasks;
    }

    public List<Integer> getParticipatedTaskEvaluations() {
        return participatedTaskEvaluations;
    }

    public void setParticipatedTaskEvaluations(List<Integer> participatedTaskEvaluations) {
        this.participatedTaskEvaluations = participatedTaskEvaluations;
    }

    public List<Integer> getOnGoingTaskParticipation() {
        return onGoingTaskParticipation;
    }

    public void setOnGoingTaskParticipation(List<Integer> onGoingTaskParticipation) {
        this.onGoingTaskParticipation = onGoingTaskParticipation;
    }

    public List<Integer> getTaskEvaluationsParticipation() {
        return taskEvaluationsParticipation;
    }

    public void setTaskEvaluationsParticipation(List<Integer> taskEvaluationsParticipation) {
        this.taskEvaluationsParticipation = taskEvaluationsParticipation;
    }
}

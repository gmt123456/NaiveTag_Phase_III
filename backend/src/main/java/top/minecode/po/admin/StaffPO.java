package top.minecode.po.admin;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.List;
import java.util.Map;

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
    private Map<Integer, Integer> participatedTaskEvaluations;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Integer.class)
    @Fetch(FetchMode.SUBSELECT)
    private Map<Integer, Integer> taskEvaluationsParticipation;

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

    public Map<Integer, Integer> getParticipatedTaskEvaluations() {
        return participatedTaskEvaluations;
    }

    public void setParticipatedTaskEvaluations(Map<Integer, Integer> participatedTaskEvaluations) {
        this.participatedTaskEvaluations = participatedTaskEvaluations;
    }

    public Map<Integer, Integer> getTaskEvaluationsParticipation() {
        return taskEvaluationsParticipation;
    }

    public void setTaskEvaluationsParticipation(Map<Integer, Integer> taskEvaluationsParticipation) {
        this.taskEvaluationsParticipation = taskEvaluationsParticipation;
    }
}

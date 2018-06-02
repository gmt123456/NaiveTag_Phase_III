package top.minecode.po.task;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.List;

/**
 * Created on 2018/6/2.
 * Description:
 *
 * @author iznauy
 */
@Entity
public class CheckTaskPO {

    @Id
    private int id;

    private String staffEmail;

    private int taskId;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Integer.class)
    @Fetch(FetchMode.SUBSELECT)
    private List<Integer> doingParticipationIds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStaffEmail() {
        return staffEmail;
    }

    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public List<Integer> getDoingParticipationIds() {
        return doingParticipationIds;
    }

    public void setDoingParticipationIds(List<Integer> doingParticipationIds) {
        this.doingParticipationIds = doingParticipationIds;
    }
}

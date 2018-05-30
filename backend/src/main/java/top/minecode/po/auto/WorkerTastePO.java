package top.minecode.po.auto;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import top.minecode.domain.task.TaskTag;
import top.minecode.domain.task.TaskType;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2018/5/26.
 * Description:
 *
 * @author iznauy
 */
@Entity
public class WorkerTastePO implements Serializable {

    @Id
    private String email;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Double.class)
    @Fetch(FetchMode.SUBSELECT)
    private Map<TaskType, Double> typeClickTimes;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Double.class)
    @Fetch(FetchMode.SUBSELECT)
    private Map<TaskTag, Double> tagClickTimes;

    public WorkerTastePO(){

    }

    public WorkerTastePO(String email) {
        this.email = email;

        this.typeClickTimes = new HashMap<>();
        typeClickTimes.put(TaskType.t_100, 0.0);
        typeClickTimes.put(TaskType.t_101, 0.0);
        typeClickTimes.put(TaskType.t_200, 0.0);
        typeClickTimes.put(TaskType.t_201, 0.0);
        typeClickTimes.put(TaskType.t_300, 0.0);
        typeClickTimes.put(TaskType.t_301, 0.0);
        typeClickTimes.put(TaskType.t_400, 0.0);
        typeClickTimes.put(TaskType.t_401, 0.0);

        this.tagClickTimes = new HashMap<>();
        tagClickTimes.put(TaskTag.military, 0.0);
        tagClickTimes.put(TaskTag.nature, 0.0);
        tagClickTimes.put(TaskTag.sports, 0.0);
        tagClickTimes.put(TaskTag.humanity, 0.0);
        tagClickTimes.put(TaskTag.science, 0.0);
        tagClickTimes.put(TaskTag.others, 0.0);

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<TaskType, Double> getTypeClickTimes() {
        return typeClickTimes;
    }

    public void setTypeClickTimes(Map<TaskType, Double> typeClickTimes) {
        this.typeClickTimes = typeClickTimes;
    }

    public Map<TaskTag, Double> getTagClickTimes() {
        return tagClickTimes;
    }

    public void setTagClickTimes(Map<TaskTag, Double> tagClickTimes) {
        this.tagClickTimes = tagClickTimes;
    }
}

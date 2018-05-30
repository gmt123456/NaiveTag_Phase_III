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
public class WorkerAbilityPO implements Serializable {

    @Id
    private String email;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Double.class)
    @Fetch(FetchMode.SUBSELECT)
    private Map<TaskType, Double> typeToAbility;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Double.class)
    @Fetch(FetchMode.SUBSELECT)
    private Map<TaskTag, Double> tagToAbility;

    public WorkerAbilityPO() {
    }

    public WorkerAbilityPO(String email) {
        this.email = email;

        this.typeToAbility = new HashMap<>();
        typeToAbility.put(TaskType.t_100, 0.5);
        typeToAbility.put(TaskType.t_101, 0.5);
        typeToAbility.put(TaskType.t_200, 0.5);
        typeToAbility.put(TaskType.t_201, 0.5);
        typeToAbility.put(TaskType.t_300, 0.5);
        typeToAbility.put(TaskType.t_301, 0.5);
        typeToAbility.put(TaskType.t_400, 0.5);
        typeToAbility.put(TaskType.t_401, 0.5);

        tagToAbility.put(TaskTag.military, 0.5);
        tagToAbility.put(TaskTag.humanity, 0.5);
        tagToAbility.put(TaskTag.nature, 0.5);
        tagToAbility.put(TaskTag.sports, 0.5);
        tagToAbility.put(TaskTag.science, 0.5);
        tagToAbility.put(TaskTag.others, 0.5);

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<TaskType, Double> getTypeToAbility() {
        return typeToAbility;
    }

    public void setTypeToAbility(Map<TaskType, Double> typeToAbility) {
        this.typeToAbility = typeToAbility;
    }

    public Map<TaskTag, Double> getTagToAbility() {
        return tagToAbility;
    }

    public void setTagToAbility(Map<TaskTag, Double> tagToAbility) {
        this.tagToAbility = tagToAbility;
    }
}

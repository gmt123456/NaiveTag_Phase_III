package top.minecode.po.auto;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import top.minecode.domain.task.TaskTag;
import top.minecode.domain.task.TaskType;
import top.minecode.po.task.TaskPO;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created on 2018/5/28.
 * Description:
 *
 * @author iznauy
 */
@Entity
public class TaskVectorPO {

    @Id
    private int taskId;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Double.class)
    @Fetch(FetchMode.SUBSELECT)
    private List<Double> vector;

    public TaskVectorPO(){}

    public static TaskVectorPO fromTaskPO(TaskPO taskPO) {
        TaskVectorPO vectorPO = new TaskVectorPO();
        vectorPO.taskId = taskPO.getId();

        // 临时存储在一个列表里面，然后再转化成含有空格的字符串
        List<Double> vector = new ArrayList<>();
        vector.add(taskPO.getAdRate());

        List<TaskType> types = TaskType.getAll();
        Set<TaskType> taskTypes = taskPO.getSpecificTasks().keySet();

        double typeNorm = taskTypes.size();

        for (TaskType type: types)
            if (taskTypes.contains(type)) {
                vector.add(1.0 / typeNorm);
            } else {
                vector.add(0.0);
            }

        List<TaskTag> tags = TaskTag.getAllTags();
        List<TaskTag> taskTags = taskPO.getTaskTags();

        double tagNorm = taskTags.size();

        for (TaskTag tag: tags)
            if (taskTags.contains(tag)) {
                vector.add(1.0 / tagNorm);
            } else {
                vector.add(0.0);
            }

        vectorPO.vector = vector;

        return vectorPO;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public List<Double> getVector() {
        return vector;
    }

    public void setVector(List<Double> vector) {
        this.vector = vector;
    }
}

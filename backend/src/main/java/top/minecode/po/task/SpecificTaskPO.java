package top.minecode.po.task;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import top.minecode.domain.task.TaskType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created on 2018/5/16.
 * Description:
 *
 * @author iznauy
 */
@Entity
public class SpecificTaskPO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private TaskType taskType;

    private String taskDescription;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = String.class)
    @Fetch(FetchMode.SUBSELECT)
    private List<String> labels;

    private double dollars;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Integer.class)
    @Fetch(FetchMode.SUBSELECT)
    private List<Integer> subTasks;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public double getDollars() {
        return dollars;
    }

    public void setDollars(double dollars) {
        this.dollars = dollars;
    }

    public List<Integer> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(List<Integer> subTasks) {
        this.subTasks = subTasks;
    }

    @Override
    public String toString() {
        return "SpecificTaskPO{" +
                "id=" + id +
                ", taskType=" + taskType +
                ", taskDescription='" + taskDescription + '\'' +
                ", labels=" + labels.size() +
                ", dollars=" + dollars +
                ", subTasks=" + subTasks.size() +
                '}';
    }
}

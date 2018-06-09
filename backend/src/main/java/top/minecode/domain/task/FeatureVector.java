package top.minecode.domain.task;

import top.minecode.domain.utils.VectorHelper;
import top.minecode.po.task.TaskPO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2018/6/6.
 * Description:
 * @author Liao
 */
public class FeatureVector {

    private final String identity;
    private final List<Double> vector;

    public static FeatureVector fromTaskPO(TaskPO taskPO) {
        List<TaskTag> taskTags = taskPO.getTaskTags();
        TaskType taskType = taskPO.getSpecificTasks().keySet().iterator().next();  // Now there contains only one type in a task


        List<Double> featureVector = VectorHelper.zeros(TaskTag.values().length + TaskType.values().length);



        return null;
    }

    public FeatureVector(String identity, List<Double> vector) {
        this.identity = identity;
        this.vector = vector;
    }

    public String getIdentity() {
        return identity;
    }

    public List<Double> getVector() {
        return vector;
    }

    public FeatureVector add(double weight, FeatureVector other, double otherWeight) {
        if (vector.size() != other.vector.size() || !identity.equals(other.identity))
            throw new IllegalArgumentException("Vector's size are not equal");

        List<Double> newVector = new ArrayList<>();
        for (int i = 0; i < vector.size(); i++) {
            newVector.add(vector.get(i) * weight + other.vector.get(i) * otherWeight);
        }

        return new FeatureVector(identity, newVector);
    }
}

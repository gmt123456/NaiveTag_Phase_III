package top.minecode.domain.task;

import top.minecode.domain.utils.VectorHelper;
import top.minecode.po.auto.TaskVectorPO;
import top.minecode.po.auto.WorkerTastePO;
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
        List<Double> featureVector = TaskVectorPO.fromTaskPO(taskPO).getVector();

        return new FeatureVector(Integer.toString(taskPO.getId()), featureVector);
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

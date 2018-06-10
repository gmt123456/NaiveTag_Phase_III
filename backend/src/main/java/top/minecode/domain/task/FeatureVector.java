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
public class FeatureVector<T> {

    private final T identity;
    private final List<Double> vector;

    public FeatureVector(T identity, List<Double> vector) {
        this.identity = identity;
        this.vector = vector;
    }

    public T getIdentity() {
        return identity;
    }

    public List<Double> getVector() {
        return vector;
    }

}

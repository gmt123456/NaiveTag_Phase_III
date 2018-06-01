package top.minecode.domain.statistic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created on 2018/6/1.
 * Description:
 * @author Liao
 */
public class ChartData {

    private List<Vector> vectors = new ArrayList<>();

    public <T> void addVector(String name, List<T> values) {
        vectors.add(new Vector<>(name, values));
    }

    public void addEmptyVector(String name) {
        vectors.add(new Vector<>(name, Collections.emptyList()));
    }

    public List<Vector> getVectors() {
        return vectors;
    }

    private class Vector<E> {
        private String name;
        private List<E> values;

        private Vector(String name, List<E> values) {
            this.name = name;
            this.values = values;
        }
    }
}

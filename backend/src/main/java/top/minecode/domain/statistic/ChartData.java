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

    public static ChartData emptyChart(String... headers) {
        ChartData chartData = new ChartData();
        if (headers != null) {
            for (String s : headers)
                chartData.addEmptyVector(s);
        }

        return chartData;
    }

    private List<Vector> vectors = new ArrayList<>();
    private List<Field> fields = new ArrayList<>();

    public <T> void addVector(String name, List<T> values) {
        vectors.add(new Vector<>(name, values));
    }

    public <T> void addField(String name, T value) {
        fields.add(new Field<>(name, value));
    }

    public boolean contains(String vectorName) {
        return vectors.stream().anyMatch(vector -> vector.name.equals(vectorName));
    }

    public void addEmptyVector(String name) {
        vectors.add(new Vector<>(name, Collections.emptyList()));
    }

    public List<Vector> getVectors() {
        return vectors;
    }

    public List<Field> getFields() {
        return fields;
    }

    private class Vector<E> {
        private String name;
        private List<E> values;

        private Vector(String name, List<E> values) {
            this.name = name;
            this.values = values;
        }

        @Override
        public String toString() {
            return "Vector{" +
                    "name='" + name + '\'' +
                    ", values=" + values +
                    '}';
        }
    }

    private class Field<E> {
        private String name;
        private E value;

        public Field(String name, E value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Field{" +
                    "name='" + name + '\'' +
                    ", value=" + value +
                    '}';
        }
    }
}

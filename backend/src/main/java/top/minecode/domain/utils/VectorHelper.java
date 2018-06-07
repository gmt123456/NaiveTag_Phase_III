package top.minecode.domain.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created on 2018/5/28.
 * Description:
 *
 * @author iznauy
 */
public class VectorHelper {

    public static double dot(List<Double> vec1, List<Double> vec2) {
        assert vec1.size() == vec2.size();
        double result = 0.0;
        for (int i = 0; i < vec1.size(); i++)
            result += vec1.get(i) * vec2.get(i);
        return result;
    }

    public static double similarity(List<Double> vec1, List<Double> vec2) {
        assert vec1.size() == vec2.size();
        double numerator = dot(vec1, vec2);
        return numerator / (norm(vec1) * norm(vec2));
    }

    public static List<Double> zeros(int dim) {
        return new ArrayList<>(Collections.nCopies(dim, 0.));
    }

    private static double norm(List<Double> vector) {
        return Math.sqrt(vector.stream().mapToDouble(e -> e * e).sum());
    }
}

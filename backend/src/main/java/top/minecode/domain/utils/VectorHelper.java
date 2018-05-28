package top.minecode.domain.utils;

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

}

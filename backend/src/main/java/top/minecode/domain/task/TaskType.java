package top.minecode.domain.task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created on 2018/5/16.
 * Description:
 *
 * @author iznauy
 */
public enum TaskType {
    t_100,
    t_101,
    t_200,
    t_201,
    t_300,
    t_301,
    t_400,
    t_401;

    public static TaskType convert(int type) {
        switch (type) {
            case 100:
                return t_100;
            case 101:
                return t_101;
            case 200:
                return t_200;
            case 201:
                return t_201;
            case 300:
                return t_300;
            case 301:
                return t_301;
            case 400:
                return t_400;
            case 401:
                return t_401;
            default:
                return null;
        }
    }

    public static double getPrice(TaskType taskType) {
        double value = Double.parseDouble(taskType.toString().substring(2));
        return (int) value / 100;
    }

    public static List<TaskType> getAll() {
        List<TaskType> taskTypes = new LinkedList<>();
        taskTypes.add(t_100);
        taskTypes.add(t_101);
        taskTypes.add(t_200);
        taskTypes.add(t_201);
        taskTypes.add(t_300);
        taskTypes.add(t_301);
        taskTypes.add(t_400);
        taskTypes.add(t_401);
        return taskTypes;
    }

}

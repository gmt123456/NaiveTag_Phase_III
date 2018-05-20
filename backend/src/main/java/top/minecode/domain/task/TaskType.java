package top.minecode.domain.task;

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

}

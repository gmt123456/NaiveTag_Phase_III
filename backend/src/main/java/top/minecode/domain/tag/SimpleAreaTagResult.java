package top.minecode.domain.tag;

import top.minecode.domain.task.TaskType;

import java.util.List;

/**
 * Created on 2018/4/12.
 * Description:
 *
 * @author iznauy
 */
public class SimpleAreaTagResult extends AreaTagResult {

    public SimpleAreaTagResult() {
        setTagType(TaskType.t_400);
    }

    public SimpleAreaTagResult(List<Point> points) {
        super(TaskType.t_400, points);
    }
}

package top.minecode.domain.tag;

import top.minecode.domain.task.TaskType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2018/4/2.
 * Description:
 * @author Liao
 */
public abstract class AreaTagResult extends TagResult {

    private List<Point> points;

    public AreaTagResult() {
        this.points = new ArrayList<>();
    }

    public AreaTagResult(TaskType taskType, List<Point> points) {
        super(taskType);
        this.points = points;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }
}

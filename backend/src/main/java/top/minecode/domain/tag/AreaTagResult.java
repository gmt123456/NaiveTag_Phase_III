package top.minecode.domain.tag;

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

    public AreaTagResult(TagType tagType, List<Point> points) {
        super(tagType);
        this.points = points;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }
}

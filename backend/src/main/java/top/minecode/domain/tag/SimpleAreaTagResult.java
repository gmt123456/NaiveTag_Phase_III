package top.minecode.domain.tag;

import java.util.List;

/**
 * Created on 2018/4/12.
 * Description:
 *
 * @author iznauy
 */
public class SimpleAreaTagResult extends AreaTagResult {

    public SimpleAreaTagResult() {
        setTagType(TagType.t_400);
    }

    public SimpleAreaTagResult(List<Point> points) {
        super(TagType.t_400, points);
    }
}

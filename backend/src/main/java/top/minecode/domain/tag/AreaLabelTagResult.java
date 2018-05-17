package top.minecode.domain.tag;

import java.util.List;

/**
 * Created on 2018/4/12.
 * Description:
 *
 * @author iznauy
 */
public class AreaLabelTagResult extends AreaTagResult {

    private String label;

    public AreaLabelTagResult() {
        setTagType(TagType.t_401);
    }

    public AreaLabelTagResult(List<Point> points,
                              String label) {
        super(TagType.t_401, points);
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}

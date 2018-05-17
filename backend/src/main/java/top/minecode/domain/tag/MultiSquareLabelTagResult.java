package top.minecode.domain.tag;

import java.util.List;

/**
 * Created on 2018/4/12.
 * Description:
 *
 * @author iznauy
 */
public class MultiSquareLabelTagResult extends MultiSquareTagResult {

    public MultiSquareLabelTagResult() {
        setTagType(TagType.t_300);
    }

    public MultiSquareLabelTagResult(List<Frame> frames) {
        super(TagType.t_300, frames);
    }
}

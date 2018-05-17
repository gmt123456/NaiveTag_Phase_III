package top.minecode.domain.tag;

import java.util.List;

/**
 * Created on 2018/4/13.
 * Description:
 *
 * @author iznauy
 */
public class MultiSquareAnnotationTagResult extends MultiSquareTagResult {


    public MultiSquareAnnotationTagResult() {
        setTagType(TagType.t_301);
    }

    public MultiSquareAnnotationTagResult(List<Frame> frames) {
        super(TagType.t_301, frames);
    }
}

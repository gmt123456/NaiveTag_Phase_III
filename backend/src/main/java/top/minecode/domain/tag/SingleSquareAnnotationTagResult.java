package top.minecode.domain.tag;

/**
 * Created on 2018/4/12.
 * Description:
 *
 * @author iznauy
 */
public class SingleSquareAnnotationTagResult extends SingleSquareTagResult {

    public SingleSquareAnnotationTagResult() {
        setTagType(TagType.t_201);
    }

    public SingleSquareAnnotationTagResult(Frame frame) {
        super(TagType.t_201, frame);
    }
}

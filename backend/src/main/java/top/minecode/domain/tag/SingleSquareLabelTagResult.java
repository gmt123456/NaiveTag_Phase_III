package top.minecode.domain.tag;

/**
 * Created on 2018/4/12.
 * Description:
 *
 * @author iznauy
 */
public class SingleSquareLabelTagResult extends SingleSquareTagResult {

    public SingleSquareLabelTagResult() {
        setTagType(TagType.t_200);
    }

    public SingleSquareLabelTagResult(Frame frame) {
        super(TagType.t_200, frame);
    }
}

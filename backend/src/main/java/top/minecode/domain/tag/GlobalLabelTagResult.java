package top.minecode.domain.tag;

/**
 * Created on 2018/4/12.
 * Description:
 *
 * @author iznauy
 */
public class GlobalLabelTagResult extends GlobalTagResult {

    public GlobalLabelTagResult() {
        setTagType(TagType.t_100);
    }

    public GlobalLabelTagResult(String label) {
        super(TagType.t_100, label);
    }
}

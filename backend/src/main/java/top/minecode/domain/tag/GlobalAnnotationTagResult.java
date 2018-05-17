package top.minecode.domain.tag;

/**
 * Created on 2018/4/12.
 * Description:
 *
 * @author iznauy
 */
public class GlobalAnnotationTagResult extends GlobalTagResult {

    public GlobalAnnotationTagResult() {
        setTagType(TagType.t_101);
    }

    public GlobalAnnotationTagResult(String label) {
        super(TagType.t_101, label);
    }
}

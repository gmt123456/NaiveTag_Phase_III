package top.minecode.domain.tag;

/**
 * Created on 2018/4/2.
 * Description:
 * @author Liao
 */
public abstract class GlobalTagResult extends TagResult {

    private String label;

    public GlobalTagResult() {
    }

    public GlobalTagResult(TagType tagType, String label) {
        super(tagType);
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}

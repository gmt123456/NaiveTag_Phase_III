package top.minecode.domain.tag;

import java.io.Serializable;

/**
 * Created on 2018/4/1.
 * Description:
 * @author Liao
 */
public abstract class TagResult implements Serializable {

    private TagType tagType;

    public TagResult() {
    }

    public TagResult(TagType tagType) {
        this.tagType = tagType;
    }

    public TagType getTagType() {
        return tagType;
    }

    public void setTagType(TagType tagType) {
        this.tagType = tagType;
    }
}

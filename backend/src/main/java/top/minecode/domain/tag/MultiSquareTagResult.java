package top.minecode.domain.tag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2018/4/1.
 * Description:
 * @author Liao
 */
public abstract class MultiSquareTagResult extends TagResult {

    private List<Frame> frames;

    public MultiSquareTagResult() {
        frames = new ArrayList<>();
    }

    public MultiSquareTagResult(TagType tagType, List<Frame> frames) {
        super(tagType);
        this.frames = frames;
    }

    public List<Frame> getFrames() {
        return frames;
    }

    public void setFrames(List<Frame> frames) {
        this.frames = frames;
    }
}

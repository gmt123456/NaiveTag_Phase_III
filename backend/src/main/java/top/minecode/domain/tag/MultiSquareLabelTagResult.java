package top.minecode.domain.tag;

import top.minecode.domain.task.TaskType;

import java.util.List;

/**
 * Created on 2018/4/12.
 * Description:
 *
 * @author iznauy
 */
public class MultiSquareLabelTagResult extends MultiSquareTagResult {

    public MultiSquareLabelTagResult() {
        setTagType(TaskType.t_300);
    }

    public MultiSquareLabelTagResult(List<Frame> frames) {
        super(TaskType.t_300, frames);
    }
}

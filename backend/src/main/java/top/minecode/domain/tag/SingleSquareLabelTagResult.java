package top.minecode.domain.tag;

import top.minecode.domain.task.TaskType;

/**
 * Created on 2018/4/12.
 * Description:
 *
 * @author iznauy
 */
public class SingleSquareLabelTagResult extends SingleSquareTagResult {

    public SingleSquareLabelTagResult() {
        setTagType(TaskType.t_200);
    }

    public SingleSquareLabelTagResult(Frame frame) {
        super(TaskType.t_200, frame);
    }
}

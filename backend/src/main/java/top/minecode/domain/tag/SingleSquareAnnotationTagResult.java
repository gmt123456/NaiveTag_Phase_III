package top.minecode.domain.tag;

import top.minecode.domain.task.TaskType;

/**
 * Created on 2018/4/12.
 * Description:
 *
 * @author iznauy
 */
public class SingleSquareAnnotationTagResult extends SingleSquareTagResult {

    public SingleSquareAnnotationTagResult() {
        setTagType(TaskType.t_201);
    }

    public SingleSquareAnnotationTagResult(Frame frame) {
        super(TaskType.t_201, frame);
    }
}

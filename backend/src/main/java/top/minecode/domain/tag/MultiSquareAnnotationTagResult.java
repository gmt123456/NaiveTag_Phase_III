package top.minecode.domain.tag;

import top.minecode.domain.task.TaskType;

import java.util.List;

/**
 * Created on 2018/4/13.
 * Description:
 *
 * @author iznauy
 */
public class MultiSquareAnnotationTagResult extends MultiSquareTagResult {


    public MultiSquareAnnotationTagResult() {
        setTagType(TaskType.t_301);
    }

    public MultiSquareAnnotationTagResult(List<Frame> frames) {
        super(TaskType.t_301, frames);
    }
}

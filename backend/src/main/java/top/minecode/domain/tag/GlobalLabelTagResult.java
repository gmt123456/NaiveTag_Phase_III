package top.minecode.domain.tag;

import top.minecode.domain.task.TaskType;

/**
 * Created on 2018/4/12.
 * Description:
 *
 * @author iznauy
 */
public class GlobalLabelTagResult extends GlobalTagResult {

    public GlobalLabelTagResult() {
        setTagType(TaskType.t_100);
    }

    public GlobalLabelTagResult(String label) {
        super(TaskType.t_100, label);
    }
}

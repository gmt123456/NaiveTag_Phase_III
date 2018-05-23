package top.minecode.domain.tag;

import top.minecode.domain.task.TaskType;

/**
 * Created on 2018/4/12.
 * Description:
 *
 * @author iznauy
 */
public class GlobalAnnotationTagResult extends GlobalTagResult {

    public GlobalAnnotationTagResult() {
        setTagType(TaskType.t_101);
    }

    public GlobalAnnotationTagResult(String label) {
        super(TaskType.t_101, label);
    }
}

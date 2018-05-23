package top.minecode.domain.tag;

import top.minecode.domain.task.TaskType;

/**
 * Created on 2018/4/2.
 * Description:
 * @author Liao
 */
public abstract class GlobalTagResult extends TagResult {

    private String label;

    public GlobalTagResult() {
    }

    public GlobalTagResult(TaskType taskType, String label) {
        super(taskType);
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}

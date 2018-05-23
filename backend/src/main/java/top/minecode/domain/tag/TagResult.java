package top.minecode.domain.tag;

import top.minecode.domain.task.TaskType;

import java.io.Serializable;

/**
 * Created on 2018/4/1.
 * Description:
 * @author Liao
 */
public abstract class TagResult implements Serializable {

    private TaskType tagType;

    public TagResult() {
    }

    public TagResult(TaskType taskType) {
        this.tagType = tagType;
    }

    public TaskType getTagType() {
        return tagType;
    }

    public void setTagType(TaskType tagType) {
        this.tagType = tagType;
    }
}

package top.minecode.domain.tag;

import top.minecode.domain.task.TaskType;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created on 2018/6/4.
 * Description:
 *
 * @author iznauy
 */
public class TaskResult {

    private List<SubTaskResult> subTasks;

    public TaskResult() {
        this.subTasks = new LinkedList<>();
    }

    public TaskResult(List<SubTaskResult> subTasks) {
        this.subTasks = subTasks;
    }

    public List<SubTaskResult> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(List<SubTaskResult> subTasks) {
        this.subTasks = subTasks;
    }

    public void addSubTasks(SubTaskResult subTaskResult) {
        subTasks.add(subTaskResult);
    }


}

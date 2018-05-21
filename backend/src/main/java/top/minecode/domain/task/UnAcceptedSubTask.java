package top.minecode.domain.task;

import java.util.List;

/**
 * Created on 2018/5/21.
 * Description:
 *
 * @author iznauy
 */
public class UnAcceptedSubTask extends SubTaskDetail {

    private List<String> picList;

    public UnAcceptedSubTask() {
    }

    public UnAcceptedSubTask(int taskId, int subTaskId, SubTaskParticipationState taskState,
                             String taskName, TaskType taskType, String taskDescription, List<String> picList) {
        super(taskId, subTaskId, taskState, taskName, taskType, taskDescription);
        this.picList = picList;
    }

    public List<String> getPicList() {
        return picList;
    }

    public void setPicList(List<String> picList) {
        this.picList = picList;
    }
}

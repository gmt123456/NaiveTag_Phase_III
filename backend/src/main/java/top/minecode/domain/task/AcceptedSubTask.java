package top.minecode.domain.task;

import java.util.Date;
import java.util.List;

/**
 * Created on 2018/5/21.
 * Description:
 *
 * @author iznauy
 */
public class AcceptedSubTask extends SubTaskDetail {

    private List<String> finishedPicList;

    private List<String> unFinishedPicList;

    private Date endDate;

    public AcceptedSubTask() {
    }

    public AcceptedSubTask(int taskId, int subTaskId, SubTaskParticipationState taskState,
                           String taskName, TaskType taskType, String taskDescription, List<String> finishedPicList,
                           List<String> unFinishedPicList, Date endDate) {
        super(taskId, subTaskId, taskState, taskName, taskType, taskDescription);
        this.finishedPicList = finishedPicList;
        this.unFinishedPicList = unFinishedPicList;
        this.endDate = endDate;
    }

    public List<String> getFinishedPicList() {
        return finishedPicList;
    }

    public void setFinishedPicList(List<String> finishedPicList) {
        this.finishedPicList = finishedPicList;
    }

    public List<String> getUnFinishedPicList() {
        return unFinishedPicList;
    }

    public void setUnFinishedPicList(List<String> unFinishedPicList) {
        this.unFinishedPicList = unFinishedPicList;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}

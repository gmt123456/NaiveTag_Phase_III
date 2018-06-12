package top.minecode.dao.requester.task;

import top.minecode.domain.task.TaskRequirement;
import top.minecode.domain.task.TaskState;
import top.minecode.domain.task.requester.RequesterSubTaskItem;
import top.minecode.domain.task.requester.RequesterTaskItem;
import top.minecode.domain.task.requester.RequesterTaskDetails;
import top.minecode.domain.task.requester.TaskParticipant;
import top.minecode.po.task.SpecificTaskPO;
import top.minecode.po.task.TaskPO;

import java.util.List;

/**
 * Created on 2018/5/24.
 * Description:
 * @author Liao
 */
public interface RequesterTaskDao {

    /**
     * Get task information objects for requester's task list
     * @param email requester's email
     * @param state task's state
     * @see TaskState
     * @return list of task items
     */
    List<RequesterTaskItem> getTaskItems(String email, TaskState state);

    /**
     * Get all participants of this task
     * @param taskId task's id
     * @return list of participants
     */
    List<TaskParticipant> getTaskParticipants(int taskId);

    /**
     * Get sub-task information list
     * @param taskId task's id
     * @return list of sub-task information
     */
    List<RequesterSubTaskItem> getSubTaskItem(int taskId);

    RequesterTaskDetails getTaskDetails(int taskId);

    String getReadme(int taskId);

    boolean updateReadme(int taskId, String readme);

    String getResultFilePath(int taskId);

    boolean addTask(TaskPO taskPO, List<SpecificTaskPO> specificTaskPOS, String pictureDir);

    boolean changeRequirement(int taskId, TaskRequirement requirement);
}

package top.minecode.service.requester.task;

import top.minecode.domain.task.requester.RequesterSubTaskItem;
import top.minecode.domain.task.requester.RequesterTaskDetails;
import top.minecode.domain.task.requester.RequesterTaskItem;
import top.minecode.domain.task.requester.TaskParticipant;
import top.minecode.domain.utils.ResultMessage;
import top.minecode.web.requester.task.NewTaskCommand;

import java.io.IOException;
import java.util.List;

/**
 * Created on 2018/5/24.
 * Description:
 * @author Liao
 */
public interface RequesterTaskInfoService {

    /**
     * Get requester's tasks which is still ongoing.
     * @param email requester's email
     * @return list of ongoing tasks
     */
    List<RequesterTaskItem> getOnGoingTasks(String email);

    /**
     * Get requester's tasks which have been already completed.
     * @param email requester's email
     * @return list of completed tasks
     */
    List<RequesterTaskItem> getCompletedTasks(String email);

    /**
     * Get task's participants. This will return a list
     * with item's number less than limit. And the items
     * in the returned list are ordered by their completed
     * pictures number.
     * @param taskId task's id
     * @param limit limit for how many people will be returned
     * @return list of task participants
     */
    List<TaskParticipant> getParticipants(int taskId, int limit);

    /**
     * Get task details. Task details contains all information
     * which a RequesterTaskItem have, subTask's types and tasks's process
     * @param taskId task's id
     * @return task's details
     */
    RequesterTaskDetails getTaskDetails(int taskId);

    /**
     * Get readme file for task
     * @param taskId task's id
     * @return readme's content
     */
    String getReadme(int taskId);

    /**
     * Update task's readme's by content passed in.
     * @param taskId task's id
     * @param content new readme content
     * @return success <tt>ResultMessage</tt> if update successfully,
     * failure <tt>ResultMessage</tt> if something wrong with database
     */
    ResultMessage editReadme(int taskId, String content);

    /**
     * Get result file's path of this task. If this task is still ongoing
     * or it hasn't been settled, this method will return null
     * @param taskId tasks's id
     * @return result file's path if file exists, null otherwise
     */
    String getResultFile(int taskId);

    /**
     * Get information about sub-tasks of task which contains
     * type, process and participants number information
     * @param taskId task's id
     * @return list of sub-task information
     */
    List<RequesterSubTaskItem> getSubTasksInfo(int taskId);

}

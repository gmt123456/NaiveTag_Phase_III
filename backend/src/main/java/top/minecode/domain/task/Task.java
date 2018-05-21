package top.minecode.domain.task;

import top.minecode.po.task.TaskPO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
public class Task {

    private String name;

    private int taskId;

    private String taskDescription;

    private String taskCover;

    private List<TaskType> taskTypes;

    private Date endDate;

    private List<TaskTag> taskTags;

    private double totalDollars;

    public Task(Task task) {
        this.name = task.name;
        this.taskId = task.taskId;
        this.taskDescription = task.taskDescription;
        this.taskCover = task.taskCover;
        this.taskTags = task.taskTags;
        this.taskTypes = task.taskTypes;
        this.endDate = task.endDate;
        this.totalDollars = task.totalDollars;
    }

    public static Task fromPO(TaskPO po) {
        String name = po.getTaskName();
        int taskId = po.getId();
        String description = po.getTaskDescription();
        String taskCover = po.getBackgroundImage();
        List<TaskTag> taskTags = po.getTaskTags();
        Date endDate = po.getEndDate();
        List<TaskType> taskTypes = new ArrayList<>(po.getSpecificTasks().keySet());
        double totalDollars = po.getTotalDollars();
        return new Task(name, taskId, description, taskCover, taskTypes, endDate, taskTags, totalDollars);
    }

    public Task() {
    }



    public Task(String name, int taskId, String taskDescription,
                String taskCover, List<TaskType> taskTypes, Date endDate,
                List<TaskTag> taskTags, double totalDollars) {
        this.name = name;
        this.taskId = taskId;
        this.taskDescription = taskDescription;
        this.taskCover = taskCover;
        this.taskTypes = taskTypes;
        this.endDate = endDate;
        this.taskTags = taskTags;
        this.totalDollars = totalDollars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskCover() {
        return taskCover;
    }

    public void setTaskCover(String taskCover) {
        this.taskCover = taskCover;
    }

    public List<TaskType> getTaskTypes() {
        return taskTypes;
    }

    public void setTaskTypes(List<TaskType> taskTypes) {
        this.taskTypes = taskTypes;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<TaskTag> getTaskTags() {
        return taskTags;
    }

    public void setTaskTags(List<TaskTag> taskTags) {
        this.taskTags = taskTags;
    }

    public double getTotalDollars() {
        return totalDollars;
    }

    public void setTotalDollars(double totalDollars) {
        this.totalDollars = totalDollars;
    }


}

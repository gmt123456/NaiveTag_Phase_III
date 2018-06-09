package top.minecode.web.staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.minecode.domain.task.SubTaskParticipationState;
import top.minecode.domain.task.TaskType;
import top.minecode.service.staff.StaffTaskService;
import top.minecode.web.common.BaseController;
import top.minecode.web.common.WebConfig;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 2018/6/1.
 * Description:
 *
 * @author iznauy
 */
@Controller
@RequestMapping("staff/task")
public class StaffTaskController extends BaseController {

    private StaffTaskService taskService;

    public StaffTaskService getTaskService() {
        return taskService;
    }

    @Autowired
    public void setTaskService(StaffTaskService taskService) {
        this.taskService = taskService;
    }

    @RequestMapping("/join")
    @ResponseBody
    public String joinTask(HttpServletRequest request, int taskId) {
        String email = getStaffEmail(request);
        return WebConfig.getGson().toJson(taskService.joinTask(email, taskId));
    }

    @RequestMapping(value = "/subTaskSet")
    @ResponseBody
    public String getSubTaskSet(HttpServletRequest request, int taskId, int taskType) {
        String email = getStaffEmail(request);
        TaskType type = TaskType.convert(taskType);
        return WebConfig.getGson().toJson(taskService.getAllSubTasks(email, taskId, type));
    }

    @RequestMapping(value = "/myParticipation")
    @ResponseBody
    public String getMyParticipation(HttpServletRequest request, int taskId, int subTaskState) {
        String email = getStaffEmail(request);
        SubTaskParticipationState state = SubTaskParticipationState.convert(subTaskState);
        return WebConfig.getGson().toJson(taskService.getWorkerParticipation(email, taskId, state));
    }

    @RequestMapping(value = "/index")
    @ResponseBody
    public String getTasks(HttpServletRequest request) {
        String email = getStaffEmail(request);
        return WebConfig.getGson().toJson(taskService.getTasks(email));
    }

    @RequestMapping(value = "/main")
    @ResponseBody
    public String getTaskInfo(HttpServletRequest request, int taskId) {
        String email = getStaffEmail(request);
        return WebConfig.getGson().toJson(taskService.getTaskDetail(email, taskId));
    }

    @RequestMapping(value = "/ongoing")
    @ResponseBody
    public String participatedTasks(HttpServletRequest request) {
        String email = getStaffEmail(request);
        return WebConfig.getGson().toJson(taskService.getParticipatedTasks(email));
    }

}

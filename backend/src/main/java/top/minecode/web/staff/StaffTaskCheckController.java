package top.minecode.web.staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.minecode.domain.task.TaskType;
import top.minecode.service.staff.StaffTaskCheckService;
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
@RequestMapping(value = "/staff/check")
public class StaffTaskCheckController extends BaseController {

    private StaffTaskCheckService checkService;

    public StaffTaskCheckService getCheckService() {
        return checkService;
    }

    @Autowired
    public void setCheckService(StaffTaskCheckService checkService) {
        this.checkService = checkService;
    }

    @RequestMapping(value = "/index") // 查看所有的待检查的一级任务
    @ResponseBody
    public String getAllUncheckedTasks(HttpServletRequest request) {
        String email = getStaffEmail(request);
        return WebConfig.getGson().toJson(checkService.getAllUnfinishedTasks(email));
    }

    @RequestMapping(value = "/myParticipation") // 查看所有的待检查的一级任务
    @ResponseBody
    public String getMyParticipation(HttpServletRequest request) {
        String email = getStaffEmail(request);
        return WebConfig.getGson().toJson(checkService.getMyParticipation(email));
    }

    @RequestMapping(value = "/main") // 点开某一个具体的一级任务
    @ResponseBody
    public String getTaskSpecification(HttpServletRequest request, int taskId) { // 查看某个具体的任务
        String email = getStaffEmail(request);
        return WebConfig.getGson().toJson(checkService.getTaskSpecification(email, taskId));
    }

    @RequestMapping(value = "/join") // 接受一级任务的审核
    @ResponseBody
    public String joinTaskCheck(HttpServletRequest request, int taskId) {
        String email = getStaffEmail(request);
        return WebConfig.getGson().toJson(checkService.joinTask(email, taskId));
    }

    @RequestMapping(value = "/subTasks") // 查看某个一级任务下面的具体的小任务s
    @ResponseBody
    public String getUnCheckedSubTasks(HttpServletRequest request, int taskId, int taskType) {
        TaskType type = TaskType.convert(taskType);
        String email = getStaffEmail(request);
        return WebConfig.getGson().toJson(checkService.getUnCheckedSubTasks(taskId, type));
    }

    @RequestMapping(value = "/subTask/accept") // 接受某个下属小任务
    @ResponseBody
    public String acceptCheckedSubTasks(HttpServletRequest request, int taskId, int participationId) {
        String email = getStaffEmail(request);
        return WebConfig.getGson().toJson(checkService.acceptSubCheckTask(email, participationId, taskId));
    }

    @RequestMapping(value = "/subTask/ongoing") // MY PARTICIPATION
    @ResponseBody
    public String getMyUnfinishedSubTaskChecks(HttpServletRequest request, int taskId, int taskType) {
        String email = getStaffEmail(request);
        TaskType type = TaskType.convert(taskType);
        return WebConfig.getGson().toJson(checkService.getOnGoingSubTaskChecks(taskId, email, type));
    }



}

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

    @RequestMapping(value = "/index")
    @ResponseBody
    public String getAllUncheckedTasks(HttpServletRequest request) {
        return WebConfig.getGson().toJson(checkService.getAllUnfinishedTasks());
    }

    @RequestMapping(value = "/main")
    @ResponseBody
    public String getTaskSpecification(HttpServletRequest request, int taskId) { // 查看某个具体的任务
        String email = getStaffEmail(request);
        return WebConfig.getGson().toJson(checkService.getTaskSpecification(email, taskId));
    }

    @RequestMapping(value = "/join")
    @ResponseBody
    public String joinTaskCheck(HttpServletRequest request, int taskId) {
        String email = getStaffEmail(request);
        return WebConfig.getGson().toJson(checkService.joinTask(email, taskId));
    }

    @RequestMapping(value = "/subTasks")
    @ResponseBody
    public String getUnCheckedSubTasks(HttpServletRequest request, int taskId, int taskType) {
        TaskType type = TaskType.convert(taskType);
        String email = getStaffEmail(request);
        return null;
    }

    @RequestMapping(value = "/subTask/accept")
    @ResponseBody
    public String acceptCheckedSubTasks(HttpServletRequest request, int taskId, int participationId) {
        String email = getStaffEmail(request);
        return null;
    }



}

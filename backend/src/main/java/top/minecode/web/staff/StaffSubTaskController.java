package top.minecode.web.staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.minecode.domain.task.*;
import top.minecode.service.staff.StaffSubTaskService;
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
@RequestMapping(value = "/staff/subTask")
public class StaffSubTaskController extends BaseController {

    private StaffSubTaskService subTaskService;

    public StaffSubTaskService getSubTaskService() {
        return subTaskService;
    }

    @Autowired
    public void setSubTaskService(StaffSubTaskService subTaskService) {
        this.subTaskService = subTaskService;
    }

    @RequestMapping(value = "/details")
    @ResponseBody
    public String getSubTaskDetail(HttpServletRequest request, int taskId, int taskType, int subTaskId) {
        String email = getStaffEmail(request);
        SubTaskDetail subTaskDetail = subTaskService.getSubTaskDetail(email, taskId, subTaskId, TaskType.convert(taskType));
        if (subTaskDetail.getTaskState() == SubTaskParticipationState.DOING) {
            return WebConfig.getGson().toJson((AcceptedSubTask)subTaskDetail, AcceptedSubTask.class);
        } else {
            return WebConfig.getGson().toJson((UnAcceptedSubTask) subTaskDetail, UnAcceptedSubTask.class);
        }
    }

    @RequestMapping(value = "/subTask/accept")
    @ResponseBody
    public String acceptSubTask(HttpServletRequest request, int taskId, int taskType, int subTaskId) {
        String staffEmail = getStaffEmail(request);
        return WebConfig.getGson().toJson(subTaskService.acceptSubTask(staffEmail, taskId,
                subTaskId, TaskType.convert(taskType)));
    }

    @RequestMapping(value = "/subTask/commit")
    @ResponseBody
    public String commitSubTask(HttpServletRequest request, int taskId, int subTaskId, int taskType) {
        String staffEmail = getStaffEmail(request);
        return WebConfig.getGson().toJson(subTaskService.commitTask(staffEmail, taskId,
                subTaskId, TaskType.convert(taskType)));
    }


}

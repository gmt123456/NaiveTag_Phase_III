package top.minecode.web.workertask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.minecode.domain.task.*;
import top.minecode.domain.utils.Pair;
import top.minecode.service.workertask.WorkerSpecificTaskService;
import top.minecode.web.common.BaseController;
import top.minecode.web.common.WebConfig;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
@Controller
@RequestMapping(value = "/worker/task")
public class WorkerSubTaskController extends BaseController {

    private WorkerSpecificTaskService specificTaskService;

    public WorkerSpecificTaskService getSpecificTaskService() {
        return specificTaskService;
    }

    @Autowired
    public void setSpecificTaskService(WorkerSpecificTaskService specificTaskService) {
        this.specificTaskService = specificTaskService;
    }

    @RequestMapping(value = "/index")
    @ResponseBody
    public String getTaskDetail(HttpServletRequest request, int taskId) {
        String email = getUserEmail(request);
        return WebConfig.getGson().toJson(specificTaskService.getTaskDetail(email, taskId));
    }

    @RequestMapping(value = "/subTask/details")
    @ResponseBody
    public String getSubTaskDetails(HttpServletRequest request, int taskId, int subTaskId, int taskType) {
        String email = getUserEmail(request);
        SubTaskDetail subTaskDetail = specificTaskService.getSubTaskDetail
                (email, taskId, subTaskId, TaskType.convert(taskType));
        if (subTaskDetail.getTaskState() == SubTaskParticipationState.DOING) {
            return WebConfig.getGson().toJson((AcceptedSubTask)subTaskDetail, AcceptedSubTask.class);
        } else {
            return WebConfig.getGson().toJson((UnAcceptedSubTask) subTaskDetail, UnAcceptedSubTask.class);
        }
    }

    @RequestMapping(value = "/subTask/accept")
    @ResponseBody
    public String acceptSubTask(HttpServletRequest request, int taskId, int taskType, int subTaskId) {
        String email = getUserEmail(request);
        return WebConfig.getGson().toJson(specificTaskService
                .acceptSubTask(email, taskId, subTaskId, TaskType.convert(taskType)));
    }

    @RequestMapping(value = "/subTask/commit")
    @ResponseBody
    public String commitSubTask(HttpServletRequest request, int taskId, int subTaskId, int taskType) {
        return null;
    }



}

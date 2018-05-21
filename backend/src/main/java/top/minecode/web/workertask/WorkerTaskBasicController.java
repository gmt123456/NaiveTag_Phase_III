package top.minecode.web.workertask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.minecode.domain.task.SubTaskParticipation;
import top.minecode.domain.task.SubTaskParticipationState;
import top.minecode.domain.task.TaskType;
import top.minecode.service.workertask.WorkerTaskBasicService;
import top.minecode.web.common.BaseController;
import top.minecode.web.common.WebConfig;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
@RequestMapping("/worker")
public class WorkerTaskBasicController extends BaseController{

    private WorkerTaskBasicService basicService;

    public WorkerTaskBasicService getBasicService() {
        return basicService;
    }

    @Autowired
    public void setBasicService(WorkerTaskBasicService basicService) {
        this.basicService = basicService;
    }

    @RequestMapping("/join")
    @ResponseBody
    public String joinTask(HttpServletRequest request, int taskId) {
        String email = getUserEmail(request);
        return WebConfig.getGson().toJson(basicService.joinTask(email, taskId));
    }

    @RequestMapping(value = "/task/subTaskSet")
    @ResponseBody
    public String getSubTaskSet(HttpServletRequest request, int taskId, int taskType) {
        String email = getUserEmail(request);
        TaskType type = TaskType.convert(taskType);
        return WebConfig.getGson().toJson(basicService.getAllSubTasks(email, taskId, type));
    }

    @RequestMapping(value = "/task/myParticipation")
    @ResponseBody
    public String getMyParticipation(HttpServletRequest request, int taskId, int subTaskState) {
        String email = getUserEmail(request);
        SubTaskParticipationState state = SubTaskParticipationState.convert(subTaskState);
        return WebConfig.getGson().toJson(basicService.getWorkerParticipation(email, taskId, state));
    }

}

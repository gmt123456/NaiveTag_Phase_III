package top.minecode.web.requester.task;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.minecode.domain.task.requester.TaskParticipant;
import top.minecode.service.requester.task.RequesterTaskService;
import top.minecode.web.common.BaseController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created on 2018/5/24.
 * Description:
 * @author Liao
 */
@Controller
@RequestMapping("requester/task")
public class RequesterTaskController extends BaseController {

    private RequesterTaskService taskService;
    private Gson gson;

    @Autowired
    public void setTaskService(RequesterTaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    public void setGson(Gson gson) {
        this.gson = gson;
    }

    @RequestMapping("ongoing")
    @ResponseBody
    public String getOngoingTasks(HttpServletRequest request) {
        return gson.toJson(taskService.getOnGoingTasks(getUserEmail(request)));
    }

    @RequestMapping("done")
    @ResponseBody
    public String getCompletedTasks(HttpServletRequest request) {
        return gson.toJson(taskService.getCompletedTasks(getUserEmail(request)));
    }

    @RequestMapping("participants")
    @ResponseBody
    public String getParticipants(@RequestParam("taskId") String taskId) {
        List<TaskParticipant> participants = taskService.getParticipants(taskId);
        return gson.toJson(participants);
    }
}

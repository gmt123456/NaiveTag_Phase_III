package top.minecode.web.requester.task;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.minecode.service.requester.task.RequesterTaskService;
import top.minecode.web.common.BaseController;

import javax.servlet.http.HttpServletRequest;

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

    @RequestMapping("ongoing.html")
    @ResponseBody
    public String getOngoingTasks(HttpServletRequest request) {
        return gson.toJson(taskService.getOnGoingTasks(getUserEmail(request)));
    }

    @RequestMapping("done.html")
    @ResponseBody
    public String getCompletedTasks(HttpServletRequest request) {
        return gson.toJson(taskService.getCompletedTasks(getUserEmail(request)));
    }
}

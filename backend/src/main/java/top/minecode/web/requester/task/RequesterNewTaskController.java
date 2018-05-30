package top.minecode.web.requester.task;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import top.minecode.domain.task.requester.TaskCreationOptions;
import top.minecode.domain.utils.ResultMessage;
import top.minecode.service.requester.task.RequesterNewTaskService;
import top.minecode.web.common.BaseController;
import top.minecode.web.requester.info.PageCommand;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 2018/5/25.
 * Description:
 * @author Liao
 */
@Controller
@RequestMapping("requester/new")
public class RequesterNewTaskController extends BaseController {

    private RequesterNewTaskService service;
    private Gson gson;

    @Autowired
    public void setGson(Gson gson) {
        this.gson = gson;
    }

    @Autowired
    public void setService(RequesterNewTaskService service) {
        this.service = service;
    }

    @RequestMapping("/info")
    @ResponseBody
    public String getCreationInfo() {
        TaskCreationOptions options = service.getCreationInfo();

        return gson.toJson(options);
    }

    @RequestMapping("/taskOrder")
    @ResponseBody
    public String newTask(HttpServletRequest request, NewTaskCommand newTaskCommand) {

        String email = getUserEmail(request);
        ResultMessage resultMessage = service.createTask(newTaskCommand, email);
        return gson.toJson(resultMessage);
    }

    @RequestMapping("/pay")
    @ResponseBody
    public String pay(HttpServletRequest request, PayCommand payCommand) {
        payCommand.setUserEmail(getUserEmail(request));
        ResultMessage resultMessage = service.pay(payCommand);
        return gson.toJson(resultMessage);
    }
}

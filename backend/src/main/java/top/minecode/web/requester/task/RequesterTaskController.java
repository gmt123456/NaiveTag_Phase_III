package top.minecode.web.requester.task;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.minecode.domain.task.TaskRequirement;
import top.minecode.domain.task.requester.RequesterSubTaskItem;
import top.minecode.domain.task.requester.RequesterTaskDetails;
import top.minecode.domain.task.requester.TaskParticipant;
import top.minecode.service.requester.task.RequesterTaskInfoService;
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

    private static final int PARTICIPANTS_LIMIT = 20;

    private RequesterTaskInfoService taskService;
    private Gson gson;

    @Autowired
    public void setTaskService(RequesterTaskInfoService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    public void setGson(Gson gson) {
        this.gson = gson;
    }

    @RequestMapping("/ongoing")
    @ResponseBody
    public String getOngoingTasks(HttpServletRequest request) {
        return gson.toJson(taskService.getOnGoingTasks(getUserEmail(request)));
    }

    @RequestMapping("/done")
    @ResponseBody
    public String getCompletedTasks(HttpServletRequest request) {
        return gson.toJson(taskService.getCompletedTasks(getUserEmail(request)));
    }

    @RequestMapping("/participants")
    @ResponseBody
    public String getParticipants(@RequestParam("taskId") int taskId) {
        List<TaskParticipant> participants = taskService.getParticipants(taskId, PARTICIPANTS_LIMIT);
        return gson.toJson(participants);
    }

    @RequestMapping("/sketch")
    @ResponseBody
    public String getTaskDetails(@RequestParam("taskId") int taskId) {
        RequesterTaskDetails requesterTaskDetails = taskService.getTaskDetails(taskId);
        return gson.toJson(requesterTaskDetails);
    }

    @RequestMapping("/readme")
    @ResponseBody
    public String getReadMe(@RequestParam("taskId") int taskId) {
        return taskService.getReadme(taskId);
    }

    @RequestMapping("/editReadme")
    @ResponseBody
    public String editReadMe(@RequestParam("readme") String readme, @RequestParam("taskId") int taskId) {
        return gson.toJson(taskService.editReadme(taskId, readme));
    }

    @RequestMapping("/download")
    @ResponseBody
    public String download(@RequestParam("taskId") int taskId) {
        String filePath = taskService.getResultFile(taskId);
        // Return null if file not exists
        if (filePath == null)
            return null;
        return filePath;
    }

    @RequestMapping("/subtask")
    @ResponseBody
    public String getSubTaskInfo(@RequestParam("taskId") int taskId) {
        List<RequesterSubTaskItem> subTasks = taskService.getSubTasksInfo(taskId);
        return gson.toJson(subTasks);
    }

    @RequestMapping("/changeTaskRequirement")
    @ResponseBody
    public String changeRequirement(@RequestParam("taskId") int taskId, @RequestParam("taskRequirement") String taskRequirement) {
        return gson.toJson(taskService.changeTaskRequirement(taskId, TaskRequirement.valueOf(taskRequirement.toUpperCase())));
    }
}

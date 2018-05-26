package top.minecode.web.requester.task;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.minecode.domain.task.TaskTag;
import top.minecode.domain.task.requester.NewTaskInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 2018/5/25.
 * Description:
 * @author Liao
 */
@Controller
@RequestMapping("requester/new")
public class RequesterNewTaskController {

    @RequestMapping("info")
    @ResponseBody
    public String getCreationInfo() {
        JsonObject creationInfo = new JsonObject();

        // Add tags
        Gson gson = new Gson();
        creationInfo.addProperty("tags", gson.toJson(TaskTag.values()));

        // Add task types
        int[] labelTasks = new int[]{100, 200, 300, 401};
        int[] describeTask = new int[]{101, 201, 301, 400};
        creationInfo.addProperty("labelTasks", gson.toJson(labelTasks));
        creationInfo.addProperty("describeTasks", gson.toJson(describeTask));

        return gson.toJson(creationInfo);
    }

    @RequestMapping("task")
    @ResponseBody
    public String newTask(HttpServletRequest request, NewTaskInfo newTaskInfo) {
        return null;
    }
}

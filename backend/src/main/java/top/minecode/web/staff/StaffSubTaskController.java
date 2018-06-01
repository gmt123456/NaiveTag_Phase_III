package top.minecode.web.staff;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.minecode.domain.task.TaskType;
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
@RequestMapping(value = "/staff/task")
public class StaffSubTaskController extends BaseController {

    @RequestMapping(value = "/index")
    @ResponseBody
    public String getTaskDetail(HttpServletRequest request, int taskId) {
        return null;
    }

    @RequestMapping(value = "/subTask/accept")
    @ResponseBody
    public String acceptSubTask(HttpServletRequest request, int taskId, int taskType, int subTaskId) {
        return null;
    }

    @RequestMapping(value = "/subTask/commit")
    @ResponseBody
    public String commitSubTask(HttpServletRequest request, int taskId, int subTaskId, int taskType) {
        return null;
    }


}

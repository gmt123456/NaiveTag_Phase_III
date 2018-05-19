package top.minecode.web.workertask;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.minecode.web.common.BaseController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
@Controller
@RequestMapping(value = "/worker/task/subTask/")
public class WorkerSubTaskController extends BaseController {

    @RequestMapping(value = "/details")
    @ResponseBody
    public String getSubTaskDetails(HttpServletRequest request, int taskId, int subTaskId, int taskType) {
        return null;
    }

    @RequestMapping(value = "/accept")
    @ResponseBody
    public String acceptSubTask(HttpServletRequest request, int taskId, int taskType, int subTaskId) {
        return null;
    }

    @RequestMapping(value = "/commit")
    @ResponseBody
    public String commitSubTask(HttpServletRequest request, int taskId, int subTaskId, int taskType) {
        return null;
    }



}

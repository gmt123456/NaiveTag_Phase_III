package top.minecode.web.workertask;

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
@RequestMapping("/worker")
public class WorkerTaskBasicController extends BaseController{

    @RequestMapping("/join")
    @ResponseBody
    public String joinTask(HttpServletRequest request, int taskId) {
        return null;
    }

    @RequestMapping(value = "/task/subTaskSet")
    @ResponseBody
    public String getSubTaskSet(HttpServletRequest request, int taskId, int taskType) {
        return null;
    }

    @RequestMapping(value = "/task/myparticipation")
    @ResponseBody
    public String getMyParticipation(HttpServletRequest request, int taskId, String taskState) {
        return null;
    }

}

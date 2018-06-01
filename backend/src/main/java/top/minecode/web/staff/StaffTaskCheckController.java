package top.minecode.web.staff;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.minecode.web.common.BaseController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 2018/6/1.
 * Description:
 *
 * @author iznauy
 */
@Controller
@RequestMapping(value = "/staff/check")
public class StaffTaskCheckController extends BaseController {

    @RequestMapping(value = "/index")
    @ResponseBody
    public String getAllUncheckedTasks(HttpServletRequest request) {
        return null;
    }

    @RequestMapping(value = "/join")
    @ResponseBody
    public String joinTaskCheck(HttpServletRequest request, int taskId) {
        return null;
    }

    @RequestMapping(value = "/subTasks")
    @ResponseBody
    public String getUnCheckedSubTasks(HttpServletRequest request, int taskId) {
        return null;
    }

    @RequestMapping(value = "/subTask/accept")
    @ResponseBody
    public String acceptCheckedSubTasks(HttpServletRequest request, int taskId, int participationId) {
        return null;
    }

}

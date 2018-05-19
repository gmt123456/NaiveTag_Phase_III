package top.minecode.web.workertask;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping(value = "/worker/task")
public class WorkerTaskSearchController extends BaseController {

    @RequestMapping("/search")
    @ResponseBody
    public String searchTasks(HttpServletRequest request,  int taskType,
                              @RequestParam(required = false) String taskTag,
                              int rankType, int begin, int step,
                              String key, boolean canAccept) {
        return null;
    }

}

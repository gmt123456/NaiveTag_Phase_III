package top.minecode.web.tag;

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
@RequestMapping("/worker/tag")
public class TagController extends BaseController {

    @RequestMapping("/save")
    @ResponseBody
    private String save(HttpServletRequest request, int taskId, int subTaskId, int taskType, String url, String data) {
        return "666";
    }

    @RequestMapping("/next")
    @ResponseBody
    public String next(HttpServletRequest request, int taskId, int subTaskId, int taskType, String url) {
        return null;
    }

    @RequestMapping("/getLabelInfo")
    @ResponseBody
    public String getLabelInformation(HttpServletRequest request, int taskId, int taskType, int subTaskId, String url) {
        return null;
    }

    @RequestMapping("/previous")
    @ResponseBody
    public String previous(HttpServletRequest request, int taskId, int subTaskId, int taskType, String url) {
        return null;
    }

    @RequestMapping("/taskInfo")
    @ResponseBody
    public String getTaskInformation(HttpServletRequest request, int taskId, int subTaskId, int taskType) {
        return null;
    }

}

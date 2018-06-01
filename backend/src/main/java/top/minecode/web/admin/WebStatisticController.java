package top.minecode.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created on 2018/6/1.
 * Description:
 * @author Liao
 */
@Controller
@RequestMapping("admin/statistic")
public class WebStatisticController {

    @RequestMapping("activeUser")
    @ResponseBody
    public String activeUsersStatistic() {
        return null;
    }

    @RequestMapping("totalUser")
    @ResponseBody
    public String totalUsersStatistic() {
        return null;
    }

    @RequestMapping("tasks")
    @ResponseBody
    public String tasksStatistic() {
        return null;
    }
}

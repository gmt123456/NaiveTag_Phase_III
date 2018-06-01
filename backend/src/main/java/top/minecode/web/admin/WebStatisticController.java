package top.minecode.web.admin;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.minecode.dao.utils.GsonFactory;
import top.minecode.service.statistic.StatisticService;

/**
 * Created on 2018/6/1.
 * Description:
 * @author Liao
 */
@Controller
@RequestMapping("admin/statistic")
public class WebStatisticController {

    private StatisticService statisticService;
    private Gson gson;

    @Autowired
    public void setGson(Gson gson) {
        this.gson = gson;
    }

    @Autowired
    public void setStatisticService(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @RequestMapping("activeUser")
    @ResponseBody
    public String activeUsersStatistic() {
        return gson.toJson(statisticService.getActiveUserStatistic());
    }

    @RequestMapping("signUpUser")
    @ResponseBody
    public String totalUsersStatistic() {
        return gson.toJson(statisticService.getSignUpUserStatistic());
    }

    @RequestMapping("tasks")
    @ResponseBody
    public String tasksStatistic() {
        return gson.toJson(statisticService.getTasksStatistic());
    }
}

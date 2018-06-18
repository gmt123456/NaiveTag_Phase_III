package top.minecode.web.admin;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @RequestMapping(value = "/activeUser", method = RequestMethod.GET)
    @ResponseBody
    public String activeUsersStatistic() {
        return gson.toJson(statisticService.getActiveUserStatistic());
    }

    @RequestMapping(value = "/signUpUser", method = RequestMethod.GET)
    @ResponseBody
    public String totalUsersStatistic() {
        return gson.toJson(statisticService.getSignUpUserStatistic());
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    @ResponseBody
    public String tasksStatistic() {
        return gson.toJson(statisticService.getTasksStatistic());
    }


    @RequestMapping("/workerDistribution")
    @ResponseBody
    public String getWorkerDistribution() {
        return gson.toJson(statisticService.getWorkerDistribution());
    }

    @RequestMapping("/workerEvaluation")
    @ResponseBody
    public String getWorkerEvaluation() {
        return gson.toJson(statisticService.getWorkerEvaluation());
    }

    @RequestMapping("/taskEvaluation")
    @ResponseBody
    public String getTaskEvaluation() {
        return gson.toJson(statisticService.getTaskEvaluation());
    }

    @RequestMapping("/dollars")
    @ResponseBody
    public String getDollarStatistic() {
        return gson.toJson(statisticService.getDollarStatistic());
    }

    @RequestMapping("/taskPieChart")
    @ResponseBody
    public String getTaskPieChart() {
        return gson.toJson(statisticService.getTaskPieChart());
    }
}

package top.minecode.web.workertask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.minecode.service.workertask.TaskRecommendationService;
import top.minecode.web.common.BaseController;
import top.minecode.web.common.WebConfig;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
@Controller
@RequestMapping(value = "/worker/task")
public class WorkerTaskRecommendationController extends BaseController {

    private TaskRecommendationService recommendationService;

    public TaskRecommendationService getRecommendationService() {
        return recommendationService;
    }

    @Autowired
    public void setRecommendationService(TaskRecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @RequestMapping("/recommendation")
    @ResponseBody
    public String getRecommendations(HttpServletRequest request) {
        return WebConfig.getGson().toJson(recommendationService.getTaskRecommendations(getUserEmail(request)));
    }

}

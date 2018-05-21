package top.minecode.web.workerInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.minecode.service.workerInfo.WorkerInfoService;
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
@RequestMapping(value = "/worker/userInfo")
public class UserInfoController extends BaseController {

    private WorkerInfoService workerInfoService;

    public WorkerInfoService getWorkerInfoService() {
        return workerInfoService;
    }

    @Autowired
    public void setWorkerInfoService(WorkerInfoService workerInfoService) {
        this.workerInfoService = workerInfoService;
    }

    @RequestMapping(value = "main")
    @ResponseBody
    public String getBasicInfo(HttpServletRequest request) {
        String email = getUserEmail(request);
        return WebConfig.getGson().toJson(workerInfoService.getBasicInfo(email));
    }

    @RequestMapping(value = "recent")
    @ResponseBody
    public String getRecentInfo(HttpServletRequest request) {
        String email = getUserEmail(request);
        return WebConfig.getGson().toJson(workerInfoService.getRecentActivity(email));
    }

    @RequestMapping(value = "avatar")
    @ResponseBody
    public String getAvatar(HttpServletRequest request) {
        String email = getUserEmail(request);
        return workerInfoService.getAvatar(email);
    }

    @RequestMapping(value = "rank")
    @ResponseBody
    public String getRankTable(HttpServletRequest request) {
        // need to update
        return WebConfig.getGson().toJson(workerInfoService.getRankTable());
    }

    @RequestMapping(value = "finished")
    @ResponseBody
    public String getFinishedTasks(HttpServletRequest request) {
        String email = getUserEmail(request);
        return WebConfig.getGson().toJson(workerInfoService.getFinishedTasks(email));
    }

    @RequestMapping(value = "doing")
    @ResponseBody
    public String getOnGoingTasks(HttpServletRequest request) {
        String email = getUserEmail(request);
        return WebConfig.getGson().toJson(workerInfoService.getOngoingTasks(email));
    }

}

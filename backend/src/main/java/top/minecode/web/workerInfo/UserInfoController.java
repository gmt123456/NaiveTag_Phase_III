package top.minecode.web.workerInfo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import top.minecode.web.common.BaseController;

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

    @RequestMapping(value = "main")
    @ResponseBody
    public String getBasicInfo(HttpServletRequest request) {

        return null;
    }

    @RequestMapping(value = "recent")
    @ResponseBody
    public String getRecentInfo(HttpServletRequest request) {
        return null;
    }

    @RequestMapping(value = "avatar")
    @ResponseBody
    public String getAvatar(HttpServletRequest request) {
        return null;
    }

    @RequestMapping(value = "rank")
    @ResponseBody
    public String getRankTable(HttpServletRequest request) {
        return null;
    }

    @RequestMapping(value = "finished")
    @ResponseBody
    public String getFinishedTasks(HttpServletRequest request) {
        return null;
    }

    @RequestMapping(value = "doing")
    @ResponseBody
    public String getOnGoingTasks(HttpServletRequest request) {
        return null;
    }

}

package top.minecode.web.requester.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.minecode.service.requester.info.RequesterInfoService;
import top.minecode.web.common.BaseController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 2018/5/23.
 * Description:
 * @author Liao
 */
@Controller
@RequestMapping("requester/userInfo")
public class RequesterInfoController extends BaseController {

    private RequesterInfoService infoService;

    @Autowired
    @Qualifier("requesterInfoService")
    public void setInfoService(RequesterInfoService infoService) {
        this.infoService = infoService;
    }

    @RequestMapping("/main")
    @ResponseBody
    public String mainInfo(HttpServletRequest request) {
        return infoService.getMainInfo(getUserEmail(request));
    }

    @RequestMapping("/accountInfo")
    @ResponseBody
    public String accountInfo(HttpServletRequest request, PageCommand pageCommand) {
        return infoService.getAccountInfo(getUserEmail(request), pageCommand);
    }

    @RequestMapping("/recharge")
    @ResponseBody
    public String recharge(HttpServletRequest request, @RequestParam("dollars") double dollars) {
        return infoService.recharge(getUserEmail(request), dollars);
    }

    @RequestMapping("/change")
    @ResponseBody
    public String changeInfo(HttpServletRequest request, ChangeInfoCommand infoCommand) {
        return infoService.changeInfo(getUserEmail(request), infoCommand);
    }
}

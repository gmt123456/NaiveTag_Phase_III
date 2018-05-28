package top.minecode.web.requester.info;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.minecode.domain.utils.ResultMessage;
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
    private Gson gson;

    @Autowired
    public void setGson(Gson gson) {
        this.gson = gson;
    }

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

    @RequestMapping("/changeAvatar")
    @ResponseBody
    public String changeAvatar(HttpServletRequest request, ChangeAvatarCommand changeAvatarCommand) {
        ResultMessage resultMessage = infoService.changeInfo(getUserEmail(request), changeAvatarCommand);
        return gson.toJson(resultMessage);
    }

    @RequestMapping("/changeName")
    @ResponseBody
    public String changeName(HttpServletRequest request, ChangeNameCommand changeNameCommand) {
        ResultMessage resultMessage = infoService.changeInfo(getUserEmail(request), changeNameCommand);
        return gson.toJson(resultMessage);
    }

    @RequestMapping("/changePassword")
    @ResponseBody
    public String changePassword(HttpServletRequest request, ChangePwdCommand changePwdCommand) {
        String email = getUserEmail(request);
        changePwdCommand.setEmail(email);
        ResultMessage resultMessage = infoService.changeInfo(getUserEmail(request), changePwdCommand);
        return gson.toJson(resultMessage);
    }
}

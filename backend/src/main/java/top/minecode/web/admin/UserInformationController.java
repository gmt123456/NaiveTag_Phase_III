package top.minecode.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.minecode.web.common.BaseController;
import top.minecode.web.requester.info.PageCommand;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 2018/6/2.
 * Description:
 * @author Liao
 */
@Controller
@RequestMapping("admin/")
public class UserInformationController extends BaseController {

    @RequestMapping("workers")
    @ResponseBody
    public String workers(PageCommand command) {
        return null;
    }

    @RequestMapping("requester")
    @ResponseBody
    public String requester(PageCommand command) {
        return null;
    }

    @RequestMapping("searchUser")
    @ResponseBody
    public String searchUser(SearchCommand command) {
        return null;
    }

    @RequestMapping("userDetails")
    @ResponseBody
    public String userDetails(String userEmail) {
        return null;
    }

    @RequestMapping("modify/password")
    @ResponseBody
    public String changePwd(HttpServletRequest request, AdminChangeDollarsCommand changePwdCommand) {
        return null;
    }

    @RequestMapping("modify/dollars")
    @ResponseBody
    public String changeDollars(HttpServletRequest request, AdminChangePasswordCommand changeDollarsCommand) {
        return null;
    }
}

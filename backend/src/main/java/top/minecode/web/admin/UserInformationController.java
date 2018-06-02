package top.minecode.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.minecode.web.requester.info.PageCommand;

/**
 * Created on 2018/6/2.
 * Description:
 * @author Liao
 */
@Controller
@RequestMapping("admin/")
public class UserInformationController {

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
    public String changePwd() {
        return null;
    }

    @RequestMapping("modify/dollars")
    @ResponseBody
    public String changeDollars() {
        return null;
    }
}

package top.minecode.web.worker;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
@Controller
@RequestMapping(value = "/worker/userInfo")
public class UserInfoController {

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


}

package top.minecode.web.workerInfo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class UserInfoEditController extends BaseController {

    @RequestMapping("/editBasicInfo")
    @ResponseBody
    public String editUserBasicInfo(HttpServletRequest request, String userName) {
        return null;
    }

    @RequestMapping("/editAvatar")
    @ResponseBody
    public String editAvatar(HttpServletRequest request, MultipartFile avatar) {
        return null;
    }

    @RequestMapping(value = "/editPassword")
    @ResponseBody
    public String editPassword(HttpServletRequest request, String oldPassword, String newPassword) {
        return null;
    }

}

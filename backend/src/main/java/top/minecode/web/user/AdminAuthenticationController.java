package top.minecode.web.user;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.minecode.domain.utils.AuthenticationResultMessage;
import top.minecode.service.user.AdminAuthenticationService;
import top.minecode.web.common.BaseController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 2018/5/29.
 * Description:
 * @author Liao
 */
@Controller
@RequestMapping("/inside")
public class AdminAuthenticationController extends BaseController {

    private AdminAuthenticationService authenticationService;
    private Gson gson;

    @Autowired
    public void setGson(Gson gson) {
        this.gson = gson;
    }

    @Autowired
    public void setAuthenticationService(AdminAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @RequestMapping("/login")
    @ResponseBody
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        return gson.toJson((AuthenticationResultMessage) authenticationService.login(username, password), AuthenticationResultMessage.class);
    }

    @RequestMapping("/new/admin")
    @ResponseBody
    public String newAdmin(HttpServletRequest request, NewAdminCommand newAdminCommand) {
        newAdminCommand.setCurrentAdmin(getAdmin(request));
        return gson.toJson(authenticationService.createNewAdmin(newAdminCommand));
    }

    @RequestMapping("/new/staff")
    @ResponseBody
    public String newStaff(HttpServletRequest request, String email, String password) {
        String admin = getAdmin(request);
        return gson.toJson(authenticationService.createStaff(email, password, admin));
    }
}

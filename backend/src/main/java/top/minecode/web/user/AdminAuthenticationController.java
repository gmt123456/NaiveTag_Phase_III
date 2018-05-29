package top.minecode.web.user;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.minecode.service.user.AdminAuthenticationService;

/**
 * Created on 2018/5/29.
 * Description:
 * @author Liao
 */
@Controller("admin")
public class AdminAuthenticationController {

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

    @RequestMapping("login")
    @ResponseBody
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        return gson.toJson(authenticationService.login(username, password));
    }

    @RequestMapping("new")
    @ResponseBody
    public String newAdmin(NewAdminCommand newAdminCommand) {
        return gson.toJson(authenticationService.createNewAdmin(newAdminCommand));
    }
}

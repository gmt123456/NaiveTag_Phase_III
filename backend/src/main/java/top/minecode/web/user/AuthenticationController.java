package top.minecode.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.minecode.service.user.UserAuthenticationService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 2018/5/19.
 * Description:
 * @author Liao
 */
@Controller
public class AuthenticationController {

    private UserAuthenticationService authenticationService;

    @Autowired
    public void setAuthenticationService(UserAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @RequestMapping("/login")
    @ResponseBody
    public String login(LoginCommand loginCommand) {
        return authenticationService.login(loginCommand);
    }

    @RequestMapping("/logout")
    @ResponseBody
    public void logout(HttpServletRequest request) {
        authenticationService.logout(request.getParameter("token"));
    }

    @RequestMapping("/checkEmail")
    @ResponseBody
    public String checkEmail(@RequestParam("email") String email) {
        return authenticationService.verifyEmail(email);
    }

    @RequestMapping("/signup")
    @ResponseBody
    public String signup(SignupCommand signupCommand) {
        return authenticationService.signup(signupCommand);
    }
}

package top.minecode.web.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import top.minecode.domain.user.User;
import top.minecode.service.user.ActiveUserService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 2018/5/17.
 * Description:
 *
 * @author iznauy
 */
@Controller
public abstract class BaseController {

    private static final String TOKEN = "token";
    private ActiveUserService activeUserService;

    @Autowired
    @Qualifier("activeUsers")
    public void setActiveUserService(ActiveUserService activeUserService) {
        this.activeUserService = activeUserService;
    }

    protected String addUser(String userEmail) {
        return activeUserService.addUser(userEmail);
    }

    protected String getUserEmail(HttpServletRequest request) {
        return activeUserService.getUserMail(request.getParameter(TOKEN));
    }

    protected User getUser(HttpServletRequest request, String token) {
        return activeUserService.getUser(request.getParameter(TOKEN));
    }
}

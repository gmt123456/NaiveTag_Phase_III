package top.minecode.web.common;

import com.google.gson.Gson;
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
    private static final Gson gson = new Gson();
    private ActiveUserService activeUserService;

    @Autowired
    @Qualifier("activeUsers")
    public void setActiveUserService(ActiveUserService activeUserService) {
        this.activeUserService = activeUserService;
    }

    protected String getUserEmail(HttpServletRequest request) {
        // TODO: 2018/5/23 Consider the situation when the token is not in the active users' map
        return activeUserService.getUserMail(request.getParameter(TOKEN));
    }

    protected User getUser(HttpServletRequest request) {
        return activeUserService.getUser(request.getParameter(TOKEN));
    }
}

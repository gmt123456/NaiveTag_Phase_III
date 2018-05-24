package top.minecode.web.common;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import top.minecode.domain.user.User;
import top.minecode.service.user.ActiveUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

/**
 * Created on 2018/5/17.
 * Description:
 *
 * @author iznauy
 */
@Controller
public abstract class BaseController {

    private static Logger log = LoggerFactory.getLogger(BaseController.class);
    private static final String TOKEN = "token";
    private static final Gson gson = new Gson();
    private ActiveUserService activeUserService;

    @Autowired
    @Qualifier("activeUsers")
    public void setActiveUserService(ActiveUserService activeUserService) {
        this.activeUserService = activeUserService;
    }

    protected String getUserEmail(HttpServletRequest request) {
        String email = activeUserService.getUserMail(request.getParameter(TOKEN));
        if (email == null) {
            log.info("Token is not in the cache");
            throw new NoSuchElementException();
        }
        return email;
    }

    protected User getUser(HttpServletRequest request) {
        return activeUserService.getUser(request.getParameter(TOKEN));
    }
}

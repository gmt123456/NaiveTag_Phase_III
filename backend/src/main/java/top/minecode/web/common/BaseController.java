package top.minecode.web.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import top.minecode.domain.user.User;
import top.minecode.service.user.ActiveUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created on 2018/5/17.
 * Description:
 * @author iznauy
 */
@Controller
public abstract class BaseController {

    private static Logger log = LoggerFactory.getLogger(BaseController.class);
    private static final String TOKEN = "token";
    private static final String ERROR_MSG = "Token is not in the cache";
    private ActiveUserService activeUserService;

    @Autowired
    @Qualifier("activeUsers")
    public void setActiveUserService(ActiveUserService activeUserService) {
        this.activeUserService = activeUserService;
    }

    protected String getUserEmail(HttpServletRequest request) {
        return getTemplate(e -> e.getUserMail(request.getParameter(TOKEN)));
    }

    protected String getAdmin(HttpServletRequest request) {
        return getTemplate(e -> e.getAdmin(request.getParameter(TOKEN)));
    }

    protected User getUser(HttpServletRequest request) {
        return activeUserService.getUser(request.getParameter(TOKEN));
    }

    protected String getStaffEmail(HttpServletRequest request) {
        return getTemplate(e -> e.getStaff(request.getParameter(TOKEN)));
    }

    private String getTemplate(Function<ActiveUserService, String> getMethod) {
        String result = getMethod.apply(activeUserService);
        if (result == null) {
            log.info(ERROR_MSG);
            throw new NoSuchElementException();
        }
        return result;
    }
}

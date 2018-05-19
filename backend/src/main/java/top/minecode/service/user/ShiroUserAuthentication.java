package top.minecode.service.user;

import com.google.gson.Gson;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import top.minecode.dao.user.UserDao;
import top.minecode.web.user.LoginCommand;
import top.minecode.web.user.SignupCommand;


/**
 * Created on 2018/5/19.
 * Description: Implement user's authentication by shiro
 * @author Liao
 */
@Service("shiroAuthenticationService")
public class ShiroUserAuthentication implements UserAuthenticationService {

    private static final Logger log = LoggerFactory.getLogger(ShiroUserAuthentication.class);
    private static final Gson gson = new Gson();
    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    private static final String ERROR_MESSAGE = "Invalid username/password";

    private Authenticator authenticator;
    private ActiveUserService activeUserService;
    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    @Qualifier("activeUsers")
    public void setActiveUserService(ActiveUserService activeUserService) {
        this.activeUserService = activeUserService;
    }

    @Autowired
    public void setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    @Override
    public String login(LoginCommand loginCommand) {
        String email = loginCommand.getEmail();
        String password = loginCommand.getPassword();
        UsernamePasswordToken token = new UsernamePasswordToken(email, password);

        try {
            authenticator.authenticate(token);
            String webToken = activeUserService.addUser(email);

            return gson.toJson(new AuthenticationResponse(null, SUCCESS, webToken));
        } catch (AuthenticationException e) {
            log.debug("Authentication fail");
            return gson.toJson(new AuthenticationResponse(ERROR_MESSAGE, FAILURE, null));
        }
    }

    @Override
    public String signup(SignupCommand signupCommand) {

        return null;
    }

    @Override
    public void logout(String token) {

    }
}

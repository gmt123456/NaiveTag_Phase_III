package top.minecode.service.user;

import com.google.gson.Gson;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import top.minecode.dao.log.AuthenticationLogDao;
import top.minecode.dao.user.UserDao;
import top.minecode.domain.user.UserType;
import top.minecode.web.user.LoginCommand;
import top.minecode.web.user.SignupCommand;

import java.util.Date;


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
    private static final String ERROR_MESSAGE = "Invalid username or password";

    private Authenticator authenticator;
    private ActiveUserService activeUserService;
    private UserDao userDao;
    private AuthenticationLogDao authenticationLogDao;

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

    @Autowired
    public void setAuthenticationLogDao(AuthenticationLogDao authenticationLogDao) {
        this.authenticationLogDao = authenticationLogDao;
    }

    @Override
    public String login(LoginCommand loginCommand) {
        String email = loginCommand.getEmail();
        String password = loginCommand.getPassword();
        UsernamePasswordToken token = new UsernamePasswordToken(email, password);

        try {
            authenticator.authenticate(token);
            String webToken = activeUserService.addUser(email);

            // Add login record to database
            authenticationLogDao.recordLogin(email, new Date(), userDao.getUser(email).getUserType());
            return gson.toJson(new AuthenticationResponse(null, SUCCESS, webToken));
        } catch (AuthenticationException e) {
            log.debug("Authentication fail");
            return gson.toJson(new AuthenticationResponse(ERROR_MESSAGE, FAILURE, null));
        }
    }

    @Override
    public String signup(SignupCommand signupCommand) {
        String email = signupCommand.getEmail();
        String password = signupCommand.getPassword();
        String name = signupCommand.getName();
        UserType userType = UserType.valueOf(signupCommand.getUserType().toUpperCase());

        // Add this user to database
        Date joinTime = new Date();
        if (userType == UserType.WORKER) {
            userDao.addWorker(email, password, name, joinTime);
        } else if (userType == UserType.REQUESTER) {
            userDao.addRequester(email, password, name, joinTime);
        }

        // First login is the time the user sign up
        authenticationLogDao.recordSignup(email, joinTime, userType);
        authenticationLogDao.recordLogin(email, joinTime, userType);

        // Login this user and get his web token
        String webToken = activeUserService.addUser(email);
        return gson.toJson(new AuthenticationResponse(null, SUCCESS, webToken));
    }

    @Override
    public void logout(String token) {
        activeUserService.logoutUser(token);
    }

    @Override
    public String verifyEmail(String email) {
        // Check email duplicate
        if (userDao.getUser(email) != null) {
            return gson.toJson(EmailVerificationResponse.duplicate());
        }
        // todo verify this email
        return gson.toJson(EmailVerificationResponse.valid());
    }
}

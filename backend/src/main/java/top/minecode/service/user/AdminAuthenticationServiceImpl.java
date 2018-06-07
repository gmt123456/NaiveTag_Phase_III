package top.minecode.service.user;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import top.minecode.dao.user.AdminDao;
import top.minecode.domain.utils.ResultMessage;
import top.minecode.service.util.Encryptor;
import top.minecode.web.user.NewAdminCommand;

/**
 * Created on 2018/5/29.
 * Description:
 * @author Liao
 */
@Service("adminAuthenticationImpl")
public class AdminAuthenticationServiceImpl implements AdminAuthenticationService {

    private static Logger log = LoggerFactory.getLogger("AdminAuthentication");

    private Authenticator authenticator;
    private ActiveUserService activeUserService;
    private AdminDao adminDao;
    private Encryptor encryptor;

    @Autowired
    public void setActiveUserService(ActiveUserService activeUserService) {
        this.activeUserService = activeUserService;
    }

    @Autowired
    public void setEncryptor(Encryptor encryptor) {
        this.encryptor = encryptor;
    }

    @Autowired
    public void setAdminDao(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    @Autowired
    @Qualifier("adminAuthenticator")
    public void setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    @Override
    public ResultMessage login(String username, String password) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        try {
            authenticator.authenticate(token);
            String webToken = activeUserService.addAdminOrStaff(username);

            return ResultMessage.authenticationSuccess(webToken, adminDao.getUserType(username));
        } catch (AuthenticationException e) {
            log.warn("Admin authenticating failed");
            return ResultMessage.failure("Invalid username or password");
        }
    }

    @Override
    public ResultMessage createStaff(String email, String password, String admin) {
        // Check permission
        if (!adminDao.exists(admin))
            return ResultMessage.failure("Permission denied");

        if (adminDao.addStaff(email, encryptor.encrypt(password, email))) {
            log.info("Staff " + email + " created successfully");
            return ResultMessage.success();
        }

        return ResultMessage.failure();
    }

    @Override
    public ResultMessage createNewAdmin(NewAdminCommand command) {
        // Verify current user's authority
        String currentAdmin = command.getCurrentAdmin();
        if (!adminDao.hasHighestAuthority(currentAdmin))
            return ResultMessage.failure("Permission denied");

        // Permission passed
        if (adminDao.addAdmin(command.getNewAdminName(),
                encryptor.encrypt(command.getPassword(), command.getNewAdminName()))) {
            log.info("New administrator is created successfully");
            return ResultMessage.success();
        }

        return ResultMessage.failure();
    }
}

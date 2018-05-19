package top.minecode.service.user;

import top.minecode.web.user.LoginCommand;
import top.minecode.web.user.SignupCommand;

/**
 * Created on 2018/5/19.
 * Description:
 * @author Liao
 */
public interface UserAuthenticationService {
    /**
     * Login method
     * @param loginCommand an object contains user email and password
     * @return login result message
     */
    String login(LoginCommand loginCommand);

    /**
     * Signup method
     * @param signupCommand an object contains name, email and password
     * @return signup result message
     */
    String signup(SignupCommand signupCommand);

    /**
     * Just remove the user's token from records
     * @param token user's token
     */
    void logout(String token);

    String verifyEmail(String email);
}

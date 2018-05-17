package top.minecode.web.common;

/**
 * Created on 2018/5/17.
 * Description:
 *
 * @author iznauy
 */
public abstract class BaseController {
    private static final int EXPIRE_TIME = 30;

    protected void addUser(String userEmail) {
        // Generate token
        String token = ""; // TODO: 2018/5/17 generate token here
        ActiveUsers.addUser(userEmail, token);
    }

    protected String getUserEmail(String token) {
        return ActiveUsers.getUser(token);
    }
}

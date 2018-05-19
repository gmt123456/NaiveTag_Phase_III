package top.minecode.web.common;

import org.apache.shiro.crypto.hash.Md5Hash;
import top.minecode.domain.user.User;

/**
 * Created on 2018/5/17.
 * Description:
 *
 * @author iznauy
 */
public abstract class BaseController {


    protected String addUser(String userEmail) {
        return ActiveUsers.INSTANCE.addUser(userEmail);
    }

    protected String getUserEmail(String token) {
        return ActiveUsers.INSTANCE.getUserMail(token);
    }

    protected User getUser(String token) {
        return ActiveUsers.INSTANCE.getUser(token);
    }
}

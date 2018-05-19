package top.minecode.domain.user.requester;

import top.minecode.domain.user.User;
import top.minecode.domain.user.UserType;

import java.util.Date;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
public class Requester extends User {

    public Requester() {
    }

    public Requester(String email, String password,
                     double dollars, Date joinTime, String avatar) {
        super(UserType.REQUESTER, email, password, dollars, joinTime, avatar);
    }
}

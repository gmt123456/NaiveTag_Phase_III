package top.minecode.dao.user;

import org.springframework.stereotype.Repository;
import top.minecode.domain.user.User;

/**
 * Created on 2018/5/19.
 * Description:
 * @author Liao
 */
@Repository
public interface UserDao {
    User getUser(String email);
}

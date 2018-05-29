package top.minecode.service.user;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import top.minecode.dao.user.UserDao;
import top.minecode.domain.user.User;
import top.minecode.service.util.CacheItem;
import top.minecode.service.util.SimpleCache;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created on 2018/4/19.
 * Description:
 * @author iznauy
 */
@Service("activeUsers")
public class ActiveUsers implements ActiveUserService {

    private static final long EXPIRE_TIME = 30;
    private static final int HASH_TIMES = 2;
    private static final String SALT = "1926";

    private static Logger log = LoggerFactory.getLogger(ActiveUsers.class);
    private static SimpleCache<ActiveUser> userCache = new SimpleCache<>();

    private UserDao userDao;

    @Autowired
    @Qualifier("userDao")
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public String getUserMail(String token) {
        ActiveUser user = userCache.get(token);
        if (user == null)
            return null;
        return user.email;
    }

    @Override
    public String addUser(String userEmail) {
        return userCache.add(new ActiveUser(userEmail), e -> getToken(e.email));
    }

    @Override
    public void logoutUser(String token) {
        userCache.remove(token);
    }

    @Override
    public User getUser(String token) {
        ActiveUser user = userCache.get(token);
        if (user == null) {
            return null;
        }

        return userDao.getUser(user.email);
    }

    /**
     * Refresh the <tt>SimpleCache</tt> per minutes
     */
    public void refresh() {
        List<CacheItem> expiredKeys = userCache.refresh();
        log.info("Active user list update");
        log.info("Expired user's list: " + expiredKeys.toString());
    }

    /**
     * Generate a token for the user
     * @param userEmail user's email
     * @return user's token
     */
    private String getToken(String userEmail) {
        Random random = new Random();
        return new Md5Hash(userEmail, SALT + random.nextInt(), HASH_TIMES).toString();
    }

    private static class ActiveUser extends CacheItem {
        private final String email;

        private ActiveUser(String email) {
            super(EXPIRE_TIME);
            this.email = email;
        }

        @Override
        public String toString() {
            return "ActiveUser{" +
                    "email='" + email + '\'' +
                    '}';
        }
    }
}

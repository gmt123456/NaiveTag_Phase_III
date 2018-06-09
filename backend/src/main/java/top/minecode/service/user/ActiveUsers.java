package top.minecode.service.user;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import top.minecode.dao.user.UserDao;
import top.minecode.domain.user.User;
import top.minecode.service.util.CacheItem;
import top.minecode.service.util.SimpleCache;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

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
    private static SimpleCache<ActiveUser> insiderCache = new SimpleCache<>();
    private static SimpleCache<ActiveUser> staffCache = new SimpleCache<>();

    private UserDao userDao;

    @Autowired
    @Qualifier("userDao")
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public String getUserMail(String token) {
        return Optional.ofNullable(userCache.get(token)).map(e -> e.identity).orElse(null);
    }

    @Override
    public String addUser(String userEmail) {
        return userCache.add(new ActiveUser(userEmail), e -> getToken(e.identity));
    }

    @Override
    public String addAdminOrStaff(String admin) {
        return insiderCache.add(new ActiveUser(admin), e -> getToken(e.identity));
    }

    @Override
    public String getAdminOrStaff(String token) {
        return Optional.ofNullable(insiderCache.get(token)).map(e -> e.identity).orElse(null);
    }

    @Override
    public void logoutUser(String token) {
        userCache.remove(token);
    }

    @Override
    public User getUser(String token) {
        return Optional.ofNullable(userCache.get(token))
                .map(e -> userDao.getUser(e.identity)).orElse(null);
    }

    /**
     * Refresh the <tt>SimpleCache</tt> per minutes
     */
    public void refresh() {
        List<CacheItem> expiredUsers = userCache.refresh();
        List<CacheItem> expiredAdmin = insiderCache.refresh();
        List<CacheItem> expiredStaff = staffCache.refresh();
        log.info("Active user|admin|staff list update");
        log.info("Expired user's list: " + expiredUsers.toString());
        log.info("Expired admin's list: " + expiredAdmin.toString());
        log.info("Expired staff's list: " + expiredStaff.toString());
    }

    /**
     * Generate a token for the user/admin/staff
     * @param identity user/admin/staff's identity
     * @return user/admin/staff's token
     */
    private String getToken(String identity) {
        Random random = new Random();
        return new Md5Hash(identity, SALT + random.nextInt(), HASH_TIMES).toString();
    }

    private static class ActiveUser extends CacheItem {
        private final String identity;

        private ActiveUser(String identity) {
            super(EXPIRE_TIME);
            this.identity = identity;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ActiveUser that = (ActiveUser) o;
            return Objects.equals(identity, that.identity);
        }

        @Override
        public int hashCode() {

            return Objects.hash(identity);
        }

        @Override
        public String toString() {
            return "ActiveUser{" +
                    "identity='" + identity + '\'' +
                    '}';
        }
    }
}

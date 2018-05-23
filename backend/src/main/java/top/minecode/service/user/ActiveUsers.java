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

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
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

    private static ConcurrentHashMap<String, ActiveUser> tokenUserMap = new ConcurrentHashMap<>();
    private static Logger log = LoggerFactory.getLogger(ActiveUser.class);

    private UserDao userDao;

    @Autowired
    @Qualifier("userDao")
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public String getUserMail(String token) {
        ActiveUser user = tokenUserMap.get(token);
        if (user == null) {
            return null;
        }

        // update the his time
        user.updateTime();
        return user.email;
    }

    @Override
    public String addUser(String userEmail) {
        // Check whether the user already have a token
        Optional<Map.Entry<String, ActiveUser>> record = tokenUserMap.entrySet()
                .stream().filter(e -> e.getValue().email.equals(userEmail)).findFirst();

        return record.map(Map.Entry::getKey).orElse(addNewUser(userEmail));
    }

    @Override
    public void logoutUser(String token) {
        tokenUserMap.remove(token);
    }

    @Override
    public User getUser(String token) {
        ActiveUser user = tokenUserMap.get(token);
        if (user == null) {
            return null;
        }

        // update the his time
        user.updateTime();
        return userDao.getUser(user.email);
    }

    /**
     * Refresh the <tt>tokenUserMap</tt> per minutes
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    private void refresh() {
        // Scan the token user map and delete users whose time
        // exceed the EXPIRE_TIME
        LocalDateTime now = LocalDateTime.now();
        Map<String, ActiveUser> newMap = tokenUserMap.entrySet().stream().filter(e -> e.getValue().isActive(now))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        tokenUserMap.clear();
        tokenUserMap.putAll(newMap);
        log.info("Active user list update");
    }

    /**
     * Generate a token for user and add him to the <tt>tokenUserMap</tt>
     * @param userEmail user's email
     * @return user's token
     */
    private String addNewUser(String userEmail) {
        String token = new Md5Hash(userEmail, SALT, HASH_TIMES).toString();
        tokenUserMap.put(token, new ActiveUser(userEmail, LocalDateTime.now().plusMinutes(EXPIRE_TIME)));
        return token;
    }

    private static class ActiveUser {
        private final String email;
        private LocalDateTime expirationTime;

        private ActiveUser(String email, LocalDateTime expirationTime) {
            this.email = email;
            this.expirationTime = expirationTime;
        }

        private boolean isActive(LocalDateTime now) {
            return now.isBefore(expirationTime);
        }

        /**
         * Plus user's valid time with 30 minutes
         */
        private void updateTime() {
            expirationTime = expirationTime.plusMinutes(EXPIRE_TIME);
        }
    }
}

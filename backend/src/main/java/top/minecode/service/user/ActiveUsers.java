package top.minecode.service.user;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import top.minecode.dao.user.UserDao;
import top.minecode.domain.user.User;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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

    private Map<String, ActiveUser> tokenUserMap = new HashMap<>();
    private UserDao userDao;

    @Autowired
    @Qualifier("userDao")
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Get user's email by token
     * @param token user's token
     * @return user's email if this user is still active now, null otherwise
     */
    public String getUserMail(String token) {
        return Optional.ofNullable(tokenUserMap.get(token).email).orElse(null);
    }

    /**
     * This method should be invoked when a user try to login.
     * It will generate a token for the user.
     * @param userEmail user's email
     * @return token for the user
     */
    public String addUser(String userEmail) {
        // Check whether the user already have a token
        Optional<Map.Entry<String, ActiveUser>> record = tokenUserMap.entrySet()
                .stream().filter(e -> e.getValue().email.equals(userEmail)).findFirst();

        return record.map(Map.Entry::getKey).orElse(addNewUser(userEmail));
    }

    /**
     * Get a <tt>User</tt> object by token
     * @param token user's token
     * @return <tt>User</tt> if the token is valid, null otherwise
     */
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
        tokenUserMap = tokenUserMap.entrySet().stream().filter(e -> e.getValue().isActive(now))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
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

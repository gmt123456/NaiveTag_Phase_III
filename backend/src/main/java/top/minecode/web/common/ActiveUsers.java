package top.minecode.web.common;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
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
@Component
enum ActiveUsers {
    INSTANCE;

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

    String getUserMail(String token) {
        return Optional.ofNullable(tokenUserMap.get(token).email).orElse(null);
    }

    String addUser(String userEmail) {
        // Check whether the user already have a token
        Optional<Map.Entry<String, ActiveUser>> record = tokenUserMap.entrySet()
                .stream().filter(e -> e.getValue().email.equals(userEmail)).findFirst();

        return record.map(Map.Entry::getKey).orElse(addNewUser(userEmail));
    }

    User getUser(String token) {
        ActiveUser user = tokenUserMap.get(token);
        if (user == null) {
            return null;
        }

        // update the his time
        user.updateTime();
        return userDao.getUser(user.email);
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    private void refresh() {
        // Scan the token user map and delete users whose time
        // exceed the EXPIRE_TIME
        LocalDateTime now = LocalDateTime.now();
        tokenUserMap = tokenUserMap.entrySet().stream().filter(e -> e.getValue().isActive(now))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

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

        private void updateTime() {
            expirationTime = expirationTime.plusMinutes(EXPIRE_TIME);
        }
    }
}

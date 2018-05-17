package top.minecode.web.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created on 2018/4/19.
 * Description:
 * @author iznauy
 */
public class ActiveUsers {

    private static final Map<String, String> tokenUserMap = new HashMap<>();

    public static String getUser(String token) {
        return Optional.ofNullable(tokenUserMap.get(token)).orElse(null);
    }

    static void addUser(String userEmail, String token) {
        tokenUserMap.put(token, userEmail);
    }
}

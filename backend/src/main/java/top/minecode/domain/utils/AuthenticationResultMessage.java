package top.minecode.domain.utils;

/**
 * Created on 2018/5/19.
 * Description:
 * @author Liao
 */
public class AuthenticationResultMessage extends ResultMessage {
    private String token;
    private String userType;

    AuthenticationResultMessage(String message, String status, String token) {
        super(status, message);
        this.token = token;
    }

    AuthenticationResultMessage(String message, String status, String token, String userType) {
        super(status, message);
        this.token = token;
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

package top.minecode.domain.utils;

/**
 * Created on 2018/5/19.
 * Description:
 * @author Liao
 */
public class AuthenticationResultMessage extends ResultMessage {
    private String token;

    AuthenticationResultMessage(String message, String status, String token) {
        super(status, message);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

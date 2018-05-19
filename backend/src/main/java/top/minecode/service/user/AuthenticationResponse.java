package top.minecode.service.user;

/**
 * Created on 2018/5/19.
 * Description:
 * @author Liao
 */
public class AuthenticationResponse {
    private String message;
    private String status;
    private String token;

    AuthenticationResponse(String message, String status, String token) {
        this.message = message;
        this.status = status;
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

package top.minecode.po.log;

import top.minecode.domain.user.UserType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created on 2018/5/16.
 * Description:
 *
 * @author iznauy
 */
public class LoginLogPO implements Serializable {

    private int id;

    private String userEmail;

    private LocalDateTime time;

    private UserType userType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}

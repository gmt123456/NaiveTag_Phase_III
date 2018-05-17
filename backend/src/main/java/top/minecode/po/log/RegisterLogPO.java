package top.minecode.po.log;

import top.minecode.domain.user.UserType;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created on 2018/5/17.
 * Description:
 *
 * @author iznauy
 */
public class RegisterLogPO implements Serializable {

    private int id;

    private String userEmail;

    private LocalDate registerDate;

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

    public LocalDate getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}

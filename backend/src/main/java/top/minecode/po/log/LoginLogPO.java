package top.minecode.po.log;

import top.minecode.domain.user.UserType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created on 2018/5/16.
 * Description:
 *
 * @author iznauy
 */
@Entity
public class LoginLogPO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String userEmail;

    @Temporal(TemporalType.TIMESTAMP)
    private Date loginTime;

    private UserType userType;

    public LoginLogPO() {}

    public LoginLogPO(String userEmail, Date loginTime, UserType userType) {
        this.userEmail = userEmail;
        this.loginTime = loginTime;
        this.userType = userType;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

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


    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "LoginLogPO{" +
                "id=" + id +
                ", userEmail='" + userEmail + '\'' +
                ", loginTime=" + loginTime +
                ", userType=" + userType +
                '}';
    }
}

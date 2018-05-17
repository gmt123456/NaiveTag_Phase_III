package top.minecode.po.requester;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created on 2018/5/16.
 * Description:
 *
 * @author iznauy
 */
public class RequesterPO implements Serializable {

    private String email;

    private String name;

    private String password;

    private double dollars;

    private LocalDateTime joinTime;

    private String avatar;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getDollars() {
        return dollars;
    }

    public void setDollars(double dollars) {
        this.dollars = dollars;
    }

    public LocalDateTime getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(LocalDateTime joinTime) {
        this.joinTime = joinTime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}

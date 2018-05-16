package top.minecode.po.admin;

import top.minecode.domain.user.admin.AdminAuthority;

import java.io.Serializable;

/**
 * Created on 2018/5/16.
 * Description:
 *
 * @author iznauy
 */
public class AdminPO implements Serializable {

    private String userName;

    private String password;

    private AdminAuthority authority;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AdminAuthority getAuthority() {
        return authority;
    }

    public void setAuthority(AdminAuthority authority) {
        this.authority = authority;
    }
}

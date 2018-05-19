package top.minecode.po.admin;

import top.minecode.domain.user.admin.AdminAuthority;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created on 2018/5/16.
 * Description:
 *
 * @author iznauy
 */
@Entity
public class AdminPO implements Serializable {

    @Id
    private String userName;

    private String password;

    @Enumerated(EnumType.STRING)
    private AdminAuthority authority;

    public AdminPO() {
    }

    public AdminPO(String userName, String password, AdminAuthority authority) {
        this.userName = userName;
        this.password = password;
        this.authority = authority;
    }

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

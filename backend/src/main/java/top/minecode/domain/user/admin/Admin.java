package top.minecode.domain.user.admin;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
public class Admin {

    private String userName;

    private String password;

    private AdminAuthority authority;

    public Admin() {
    }

    public Admin(String userName, String password, AdminAuthority authority) {
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

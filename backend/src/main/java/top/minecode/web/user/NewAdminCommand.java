package top.minecode.web.user;

/**
 * Created on 2018/5/29.
 * Description:
 * @author Liao
 */
public class NewAdminCommand {

    private String currentAdmin;
    private String newAdminName;
    private String password;

    public String getCurrentAdmin() {
        return currentAdmin;
    }

    void setCurrentAdmin(String currentAdmin) {
        this.currentAdmin = currentAdmin;
    }

    public String getNewAdminName() {
        return newAdminName;
    }

    public void setNewAdminName(String newAdminName) {
        this.newAdminName = newAdminName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

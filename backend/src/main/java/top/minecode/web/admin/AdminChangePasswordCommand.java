package top.minecode.web.admin;

/**
 * Created on 2018/6/2.
 * Description:
 * @author Liao
 */
public class AdminChangePasswordCommand {

    private String email;
    private String newPassword;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getNewPassword() {
        return newPassword;
    }
}

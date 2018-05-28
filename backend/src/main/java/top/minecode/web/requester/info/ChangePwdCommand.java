package top.minecode.web.requester.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.minecode.po.requester.RequesterPO;
import top.minecode.service.util.Encryptor;

/**
 * Created on 2018/5/26.
 * Description:
 * @author Liao
 */
@Component
public class ChangePwdCommand implements ChangeCommand<RequesterPO> {

    private String oldPassword;
    private String newPassword;
    private String email;
    private Encryptor encryptor;

    @Override
    public void change(RequesterPO po) throws Exception {
        // Make sure encryptor and email have been
        if (email == null)
            throw new IllegalStateException("Email not assigned yet");

        // Verify old password
        String encryptedOldPwd = encryptor.encrypt(oldPassword, email);
        if (!encryptedOldPwd.equals(po.getPassword())) {
            throw new IllegalArgumentException("Wrong old password");
        }

        po.setPassword(encryptor.encrypt(newPassword, email));
    }

    @Autowired
    public void setEncryptor(Encryptor encryptor) {
        this.encryptor = encryptor;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}

package top.minecode.web.requester.info;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created on 2018/5/23.
 * Description:
 * @author Liao
 */
public class ChangeInfoCommand {

    private MultipartFile avatar;
    private List<Integer> imagePosition;
    private String name;
    private String oldPassword;
    private String newPassword;

    public ChangeInfoCommand(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }



    public MultipartFile getAvatar() {
        return avatar;
    }

    public void setAvatar(MultipartFile avatar) {
        this.avatar = avatar;
    }

    public List<Integer> getImagePosition() {
        return imagePosition;
    }

    public void setImagePosition(List<Integer> imagePosition) {
        this.imagePosition = imagePosition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

package top.minecode.web.requester.info;

import org.apache.commons.io.FileUtils;
import org.springframework.util.Base64Utils;
import top.minecode.po.requester.RequesterPO;
import top.minecode.service.util.PathUtil;
import top.minecode.service.util.RandomUtil;

import java.io.File;
import java.io.IOException;

/**
 * Created on 2018/5/26.
 * Description:
 * @author Liao
 */
public class ChangeAvatarCommand implements ChangeCommand<RequesterPO> {

    private String avatar;

    @Override
    public void change(RequesterPO po) throws Exception {
        String avatarPath = processAvatar();
        po.setAvatar(avatarPath);
    }

    private String processAvatar() throws IOException {
        String[] tempData = avatar.split("base64,");
        String suffix;
        if ("data:image/jpeg;".equalsIgnoreCase(tempData[0])) {
            suffix = ".jpg";
        } else if ("data:image/x-icon;".equalsIgnoreCase(tempData[0])) {
            suffix = ".ico";
        } else if ("data:image/gif;".equalsIgnoreCase(tempData[0])) {
            suffix = ".gif";
        } else
            throw new IllegalArgumentException("Wrong image format");

        byte[] bsPic = Base64Utils.decodeFromString(tempData[1]);
        String randomName = "avatar/" + RandomUtil.getRandomFileName() + suffix;
        String fileName = PathUtil.getBasePath() + randomName;
        File imageFile = new File(fileName);
        FileUtils.writeByteArrayToFile(imageFile, bsPic);

        return randomName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}

package top.minecode.service.util;

import org.apache.commons.io.FileUtils;
import org.springframework.util.Base64Utils;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created on 2018/5/26.
 * Description:
 * @author Liao
 */
public class ImageUtils {

    private static final String AVATAR_PATH = "avatar/";
    private static final String TASK_BACKGROUND_PATH = PathUtil.getTaskBackgroundPath();
    private static final String TASK_COVER_PATH = PathUtil.getTaskCoverPath();
    private static final String DEFAULT_TASK_COVER_PATH = PathUtil.getDefaultTaskCoverPath();
    private static final String DEFAULT_TASK_BACKGROUND_PATH = PathUtil.getDefaultTaskBackgroundPath();
    private static final String[] DEFAULT_AVATARS = new String[]{"0.png", "1.png", "2.png", "3.png", "4.jpg"};
    private static final String[] DEFAULT_BACKGROUNDS = new String[]{"0.jpg", "1.jpg", "2.jpg"};

    /**
     * Save avatar's data as file and return the file's relative
     * path
     * @param avatarData avatar's data
     * @return avatar's relative path
     * @throws IOException if something wrong happened when
     * writing avatar's data to a file
     */
    public static String transferAvatar(String avatarData) throws IOException {
        return transferImageTo(avatarData, AVATAR_PATH);
    }

    /**
     * Save task's background image data as file and return the file's relative
     * path
     * @param backgroundData background image's data
     * @return background image's relative path
     * @throws IOException if something wrong happened when
     * writing task's background image data to a file
     */
    public static String transferBackground(String backgroundData) throws IOException {
        return transferImageTo(backgroundData, TASK_BACKGROUND_PATH);
    }

    /**
     * Save task's cover(or task's avatar) image data as file and return the file's relative
     * path
     * @param coverData cover's data
     * @return cover's relative path
     * @throws IOException if something wrong happened when
     * writing task's cover image data to a file
     */
    public static String transferTaskCover(String coverData) throws IOException {
        return transferImageTo(coverData, TASK_COVER_PATH);
    }

    /**
     * Task avatar means <tt>cover</tt> in <tt>TaskPO</tt>
     * @return a random task avatar's path
     */
    public static String getRandomTaskCover() {
        Random random = new Random();

        int result = random.nextInt(DEFAULT_AVATARS.length);
        return DEFAULT_TASK_COVER_PATH + DEFAULT_AVATARS[result];
    }

    public static String getRandomTaskBackground() {
        Random random = new Random();

        return DEFAULT_TASK_BACKGROUND_PATH + DEFAULT_BACKGROUNDS[random.nextInt(3)];
    }

    private static String transferImageTo(String imageData, String path) throws IOException {
        String[] tempData = imageData.split("base64,");
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
        String randomName = path + RandomUtil.getRandomFileName() + suffix;
        String fileName = PathUtil.getBasePath() + randomName;
        File imageFile = new File(fileName);
        FileUtils.writeByteArrayToFile(imageFile, bsPic);

        return randomName;
    }
}

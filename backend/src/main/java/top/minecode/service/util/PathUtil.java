package top.minecode.service.util;

import java.io.File;

/**
 * Created on 2018/5/24.
 * Description:
 *
 * @author iznauy
 */
public class PathUtil {

    public static String getBasePath() {
        String rawString = PathUtil.class.getResource("../../../../.").getPath();
        return rawString.substring(0, rawString.length() - 16);
    }

    public static String getDefaultTaskCoverPath() {
        return "covers/";
    }

    public static String getDefaultTaskBackgroundPath() {
        return "background/";
    }

    public static String getRequesterDataRec(String email) {
        return "requester/" + email + "/data/";
    }

    public static String getRequesterDataFileRoot() {
        return "requester";
    }
}

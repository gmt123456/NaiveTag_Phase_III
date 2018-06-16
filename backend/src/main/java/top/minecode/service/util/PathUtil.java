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

    public static String coverToAbsolutePath(String logicalPath) {
        return getBasePath() + logicalPath;
    }

    public static String getTaskSpecificationPath() {
        return "specification/";
    }

    public static String getSubTaskResultPath() {
        return "subResult/";
    }

    public static String getPythonServerPath() {
        return "http://127.0.0.1:2333";
    }

    public static String getTaskCoverPath() {
        return "covers/";
    }

    static String getDefaultTaskCoverPath() {
        return "defaultImage/covers/";
    }

    static String getDefaultTaskBackgroundPath() {
        return "defaultImage/background/";
    }

    public static String getTaskBackgroundPath() {
        return "background/";
    }

    public static String getRequesterDataRec(String email) {
        return "requester/" + email + "/data/";
    }

    public static String getRequesterDataFileRoot() {
        return "requester";
    }
}

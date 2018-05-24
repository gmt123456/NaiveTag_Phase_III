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
        System.out.println();
        String rawString = PathUtil.class.getResource("../../../../.").getPath();
        return rawString.substring(0, rawString.length() - 16);
    }

}

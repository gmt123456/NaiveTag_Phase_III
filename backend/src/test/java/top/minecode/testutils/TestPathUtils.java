package top.minecode.testutils;

import top.minecode.service.util.PathUtil;

import java.io.File;

/**
 * Created on 2018/5/28.
 * Description:
 * @author Liao
 */
public class TestPathUtils {

    public static File getBase() {
        return new File(PathUtil.class.getResource("/").getPath()).getParentFile();
    }

    public static String getWebApp() {
        return new File(getBase().getParentFile(), "src\\main\\webapp").getPath();
    }
}

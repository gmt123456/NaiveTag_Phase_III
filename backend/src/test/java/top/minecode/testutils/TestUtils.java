package top.minecode.testutils;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.minecode.service.util.PathUtil;

import java.io.File;

/**
 * Created on 2018/5/28.
 * Description:
 * @author Liao
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath*:naive*")
public class TestUtils {


    public static File getBase() {
        return new File(PathUtil.class.getResource("/").getPath()).getParentFile();
    }

    public static String getWebApp() {
        return new File(getBase().getParentFile(), "src\\main\\webapp").getPath();
    }

}

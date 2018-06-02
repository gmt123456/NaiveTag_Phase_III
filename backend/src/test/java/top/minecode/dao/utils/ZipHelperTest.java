package top.minecode.dao.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.FileSystemUtils;
import top.minecode.service.util.PathUtil;

import java.io.File;
import java.util.zip.ZipFile;

import static org.junit.Assert.*;

/**
 * Created on 2018/5/27.
 * Description:
 * @author Liao
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath*:naive*")
public class ZipHelperTest {

    private String path = "C:\\Users\\liao\\Desktop\\14_5464.zip";

    @Test
    public void countEntriesInZipFile() throws Exception{

        ZipFile zipFile = new ZipFile(path);
        System.out.println(path);
        assertEquals(6, ZipHelper.countEntriesInZipFile(zipFile));
    }

    @Test
    public void unZipDataSet() throws Exception{
        File target = new File(path);
        String name = target.getName().substring(0, target.getName().lastIndexOf("."));
        File dest = new File(target.getParentFile(), name);
        ZipHelper.unZipDataSet(path, dest.getPath());

        FileSystemUtils.deleteRecursively(dest);
    }
}
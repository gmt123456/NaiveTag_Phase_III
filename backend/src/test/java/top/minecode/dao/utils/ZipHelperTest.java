package top.minecode.dao.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.FileSystemUtils;

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

    private File file;

    @Before
    public void setUp() throws Exception {
        file = new ClassPathResource("dataset.zip").getFile();
    }

    @Test
    public void countEntriesInZipFile() throws Exception{

        ZipFile zipFile = new ZipFile(file);
        System.out.println(file);
        assertEquals(75, ZipHelper.countEntriesInZipFile(zipFile));
    }

    @Test
    public void unZipDataSet() throws Exception{
        String name = file.getName().substring(0, file.getName().lastIndexOf("."));
        File dest = new File(file.getParentFile(), name);
        ZipHelper.unZipDataSet(file.getPath(), dest.getPath());

        FileSystemUtils.deleteRecursively(dest);
    }
}
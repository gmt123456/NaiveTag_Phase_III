package top.minecode.dao.utils;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * Created on 2018/5/27.
 * Description:
 * @author Liao
 */
public class ImageListsTest {

    @Test
    public void test1() {
        String file = "E:\\Anime\\background";
        ImageLists imageLists = new ImageLists(file, "Anime");
        imageLists.forEach(System.out::println);
    }
}
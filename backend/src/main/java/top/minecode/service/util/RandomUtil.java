package top.minecode.service.util;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created on 2018/5/24.
 * Description:
 *
 * @author iznauy
 */

public class RandomUtil {


    public static String getRandomFileName() {

        SimpleDateFormat simpleDateFormat;

        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

        Date date = new Date();

        String str = simpleDateFormat.format(date);

        Random random = new Random();

        int ranNum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数

        return ranNum + str;// 当前时间
    }

}
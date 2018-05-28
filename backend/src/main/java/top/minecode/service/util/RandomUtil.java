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

    private static final int RANDOM_AVATAR_NUMS = 5;

    public static String getRandomFileName() {

        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String str = simpleDateFormat.format(date);
        Random random = new Random();
        int ranNum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
        return ranNum + str;// 当前时间
    }

    /**
     * Task avatar means <tt>cover</tt> in <tt>TaskPO</tt>
     * @return a random task avatar's path
     */
    public static String getRandomTaskAvatar() {
        Random random = new Random();
        String avatarPath = PathUtil.getDefaultAvatarPath();

        int result = random.nextInt(RANDOM_AVATAR_NUMS);
        return avatarPath + result;
    }

}
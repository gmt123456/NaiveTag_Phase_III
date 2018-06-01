package top.minecode.dao.statistic;

import com.google.gson.Gson;
import org.junit.Test;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.dao.utils.GsonFactory;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created on 2018/6/1.
 * Description:
 * @author Liao
 */
public class WebStatisticDaoImplTest {

    @Test
    public void test1() {
        String hql = "select new map (t.loginTime as time, t.userType as type, count(t) as number)" +
                " from LoginLogPO t group by cast(t.loginTime as date), t.userType order by cast(t.loginTime as date)";

        List<Map> result = CommonOperation.executeSQL(Map.class, hql);

        System.out.println(result);
    }

    @Test
    public void testGetActiveUser() {
        WebStatisticDao statisticDao = new WebStatisticDaoImpl();
        Gson gson = GsonFactory.getGson();
        System.out.println(gson.toJson(statisticDao.getActiveUserData()));
    }

    @Test
    public void testGetSignUp() {
        WebStatisticDao dao = new WebStatisticDaoImpl();
        Gson gson = GsonFactory.getGson();
        System.out.println(gson.toJson(dao.getSignUpStatistic()));
    }
}
package top.minecode.dao.statistic;

import com.google.gson.Gson;
import org.junit.Test;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.dao.utils.GsonFactory;

import java.util.List;
import java.util.Map;

/**
 * Created on 2018/6/1.
 * Description:
 * @author Liao
 */
public class WebStatisticDaoImplTest {

    private Gson gson = GsonFactory.getGson();
    private WebStatisticDao webStatisticDao = new WebStatisticDaoImpl();

    @Test
    public void test1() {
        String hql = "select new map (cast(t.loginTime as date), t.userType as type, count(t) as number)" +
                " from LoginLogPO t group by cast(t.loginTime as date), t.userType order by cast(t.loginTime as date)";

        List<Map> result = CommonOperation.executeSQL(Map.class, hql);

        System.out.println(result);
    }

    @Test
    public void testGetActiveUser() {
        System.out.println(gson.toJson(webStatisticDao.getActiveUserData()));
    }

    @Test
    public void testGetSignUp() {
        System.out.println(gson.toJson(webStatisticDao.getSignUpStatistic()));
    }

    @Test
    public void testGetTaskData() {
        System.out.println(gson.toJson(webStatisticDao.getTasksData()));
    }

    @Test
    public void testGetParticipation() {
        System.out.println(webStatisticDao.getParticipationData());
    }
}
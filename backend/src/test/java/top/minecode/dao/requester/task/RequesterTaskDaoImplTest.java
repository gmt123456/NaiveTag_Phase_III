package top.minecode.dao.requester.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.po.task.TaskPO;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created on 2018/5/24.
 * Description:
 * @author Liao
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:naive-context.xml", "classpath:naive-shiro.xml"})
public class RequesterTaskDaoImplTest {

    @Test
    public void test1() {
        String hql = "select new map (p.email as email , sum(p.picAmount) as picNum) from SubTaskParticipationPO p where p.taskId=?" +
                " group by p.email order by sum(p.picAmount) desc";
        CommonOperation<TaskPO> operation = new CommonOperation<>(TaskPO.class);

        List<Map> result = operation.executeSQL(Map.class, hql, 2);
        System.out.println(result);
    }
}
package top.minecode.dao.requester.task;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.domain.task.TaskState;
import top.minecode.po.task.TaskPO;
import top.minecode.service.requester.task.RequesterNewTaskServiceImplTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created on 2018/5/24.
 * Description:
 * @author Liao
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:naive-context.xml", "classpath:naive-shiro.xml"})
public class RequesterTaskDaoImplTest {

    private CommonOperation<TaskPO> operation = new CommonOperation<>(TaskPO.class);
    private RequesterTaskDao taskDao;

    @Autowired
    public void setTaskDao(RequesterTaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Test
    @Ignore
    public void test1() {
        String hql = "select new map (p.email as email , sum(p.picAmount) as picNum) from SubTaskParticipationPO p where p.taskId=?" +
                " group by p.email order by sum(p.picAmount) desc";

        List<Map> result = CommonOperation.executeSQL(Map.class, hql, 2);
        System.out.println(result);
    }

    @Test
    @Ignore
    public void test2() {
        String hql = "select count(distinct t.email) from SubTaskParticipationPO t where t.email in :se";

        List<String> list = new ArrayList<>();
        list.add("123@email");
        list.add("1234@email");

        long actual = CommonOperation.template(s ->
                (long) s.createQuery(hql).setParameterList("se", list).iterate().next());
        assertEquals(2L, actual);
    }

    @Test
    public void testGetTaskParticipants() {
        System.out.println(taskDao.getTaskParticipants(1));
    }

    @Test
    public void testGetSubTaskItems() throws Exception{
        System.out.println(taskDao.getSubTaskItem(1));
    }

    @Test
    public void testGetTaskDetails() {
        System.out.println(taskDao.getTaskDetails(1));
    }

    @Test
    public void testGetTaskItem() {
        System.out.println(taskDao.getTaskItems("r1@mail.com", TaskState.ON_GOING));
    }
}
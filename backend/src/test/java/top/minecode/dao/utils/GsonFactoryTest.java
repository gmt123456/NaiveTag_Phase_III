package top.minecode.dao.utils;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.minecode.domain.task.requester.RequesterTaskItem;
import top.minecode.domain.task.requester.RequesterTaskDetails;
import top.minecode.domain.user.requester.AccountLog;
import top.minecode.po.task.TaskPO;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created on 2018/5/24.
 * Description:
 * @author Liao
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:naive-context.xml", "classpath:naive-shiro.xml"})
public class GsonFactoryTest {

    private Gson gson;

    @Autowired
    public void setGson(Gson gson) {
        this.gson = gson;
    }

    @Test
    public void testAccountLog() {
        String date = new Date().toString();
        AccountLog accountLog =
                new AccountLog(111111.11, "RECHARGE", 111111.11, date);

        String json = gson.toJson(accountLog);
        assertEquals("[111111.11,\"RECHARGE\",111111.11,\"" + date + "\"]", json);
    }

    @Test
    public void testTaskItem() {
        TaskPO taskPO = new TaskPO();
        taskPO.setTotalDollars(1234213.2);
        taskPO.setId(1);
        taskPO.setBeginDate(new Date());
        taskPO.setParticipators(new ArrayList<>());
        taskPO.setSpecificTasks(new HashMap<>());
        Instant instant = Instant.now().plusSeconds(24124322343312L);
        taskPO.setEndDate(Date.from(instant));

        RequesterTaskItem requesterTaskItem = new RequesterTaskItem(taskPO);

        System.out.println(gson.toJson(requesterTaskItem));
    }

    @Test
    public void testTaskDetails() {
        RequesterTaskDetails details = new RequesterTaskDetails(getTaskPO(), 0.88);

        System.out.println(gson.toJson(details));
    }

    private TaskPO getTaskPO() {
        TaskPO taskPO = new TaskPO();
        taskPO.setTotalDollars(1234213.2);
        taskPO.setId(1);
        taskPO.setBeginDate(new Date());
        taskPO.setParticipators(new ArrayList<>());
        taskPO.setSpecificTasks(new HashMap<>());
        Instant instant = Instant.now().plusSeconds(12345122);
        taskPO.setEndDate(Date.from(instant));

        return taskPO;
    }
}
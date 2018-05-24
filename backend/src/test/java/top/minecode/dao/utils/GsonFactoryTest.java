package top.minecode.dao.utils;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.minecode.domain.task.requester.TaskItem;
import top.minecode.domain.user.requester.AccountLog;
import top.minecode.po.task.TaskPO;

import java.time.Instant;
import java.time.ZoneId;
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
    public void testTaskItem() {
        TaskPO taskPO = new TaskPO();
        taskPO.setTotalDollars(1234213.2);
        taskPO.setBeginDate(new Date());
        taskPO.setParticipators(new ArrayList<>());
        taskPO.setSpecificTasks(new HashMap<>());
        Instant instant = Instant.now().plusSeconds(24124322343312L);
        taskPO.setEndDate(Date.from(instant));
        TaskItem taskItem = new TaskItem(taskPO);

        System.out.println(gson.toJson(taskItem));
    }
}
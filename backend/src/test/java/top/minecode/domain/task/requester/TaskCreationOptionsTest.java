package top.minecode.domain.task.requester;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created on 2018/5/26.
 * Description:
 * @author Liao
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:naive-*"})
public class TaskCreationOptionsTest {

    private TaskCreationOptions options;

    @Autowired
    public void setOptions(TaskCreationOptions options) {
        this.options = options;
    }

    @Test
    public void test1() {
        String expected = "TaskCreationOptions{tags=[military, nature, sports, " +
                "humanity, science, politics, others], divisions=[Novice, Contributor," +
                " Expert, Master, GrandMaster], labelTasks=[100, 200, 300, 401]," +
                " describeTasks=[101, 201, 301, 400]}";
        assertEquals(expected, options.toString());
    }
}
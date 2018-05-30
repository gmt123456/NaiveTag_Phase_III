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
public class TaskCreationOptionsTest {

    @Test
    public void test1() {
        TaskCreationOptions options = new TaskCreationOptions();
        String expected = "TaskCreationOptions{tags=[military, nature, sports, " +
                "humanity, science, others], divisions=[Novice, Contributor," +
                " Expert, Master, GrandMaster]}";
        assertEquals(expected, options.toString());
    }
}
package top.minecode.domain.utils;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created on 2018/5/26.
 * Description:
 * @author Liao
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:naive*")
public class ResultMessageTest {

    @Autowired
    private Gson gson;

    @Test
    public void test1() {
        System.out.println(gson.toJson(ResultMessage.success()));
        System.out.println(gson.toJson(ResultMessage.failure("Whatever")));
        System.out.println(gson.toJson(ResultMessage.payMessage("sidfj12", 124243.11, 123)));
    }
}
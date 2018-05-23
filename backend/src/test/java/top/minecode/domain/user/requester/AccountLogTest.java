package top.minecode.domain.user.requester;

import com.google.gson.Gson;
import org.apache.shiro.authc.Authenticator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created on 2018/5/23.
 * Description:
 * @author Liao
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:naive-context.xml", "classpath:naive-shiro.xml"})
public class AccountLogTest {

    private Gson gson;

    @Autowired
    public void setGson(Gson gson) {
        this.gson = gson;
    }

    @Test
    public void test1() {
        String date = new Date().toString();
        AccountLog accountLog =
                new AccountLog("+$111,111.11", "RECHARGE", "$111,111.11", date);

        String json = gson.toJson(accountLog);
        assertEquals("[\"+$111,111.11\",\"RECHARGE\",\"$111,111.11\",\"" + date + "\"]", json);
    }
}
package top.minecode.dao.log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:naive*")
public class AccountLogDaoImplTest {

    private AccountLogDao logDao;

    @Autowired
    public void setLogDao(AccountLogDao logDao) {
        this.logDao = logDao;
    }

    @Test
    public void getLogs() {
        assertEquals(0, logDao.getLogs("whatever", 1, 20).size());
    }
}
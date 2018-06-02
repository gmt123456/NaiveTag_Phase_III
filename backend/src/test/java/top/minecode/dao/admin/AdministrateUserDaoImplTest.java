package top.minecode.dao.admin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.minecode.service.util.Encryptor;

import static org.junit.Assert.*;

/**
 * Created on 2018/6/2.
 * Description:
 * @author Liao
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:naive*")
public class AdministrateUserDaoImplTest {

    private AdministrateUserDao administrateUserDao = new AdministrateUserDaoImpl();
    private Encryptor encryptor;

    @Autowired
    public void setEncryptor(Encryptor encryptor) {
        this.encryptor = encryptor;
    }

    @Test
    public void changePassword() {
        String email = "frog@email.com";
        administrateUserDao.changePassword(email, encryptor.encrypt("123456789", email));
    }

    @Test
    public void changeDollars() {
        double result = administrateUserDao.changeDollars("frog@email.com", -Double.MAX_VALUE);
        assertEquals(-1, result, 0.001);
    }

    @Test
    public void getWorkers() {
        System.out.println(administrateUserDao.getWorkers(1, 10));
    }

    @Test
    public void getRequester() {
        System.out.println(administrateUserDao.getRequester(1, 20));
    }

    @Test
    public void testSearchWorker() {
        System.out.println(administrateUserDao.searchWorkers("naive", 1, 20));
    }

    @Test
    public void testSearchRequester() {
        System.out.println(administrateUserDao.searchRequester("frog@email", 1, 20));
    }
}
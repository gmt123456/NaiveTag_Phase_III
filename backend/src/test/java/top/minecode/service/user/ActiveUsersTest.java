package top.minecode.service.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:naive*")
public class ActiveUsersTest {

    private ActiveUsers activeUsers;

    @Autowired
    public void setActiveUsers(ActiveUsers activeUsers) {
        this.activeUsers = activeUsers;
    }


    @Test
    public void testAddUsers() {
        String token1 = activeUsers.addUser("admin");
        String token2 = activeUsers.addUser("admin");

        assertEquals(token1, token2);
    }

    @Test
    public void testAddAdminToCache() {
        String token1 = activeUsers.addAdminOrStaff("admin");
        String token2 = activeUsers.addAdminOrStaff("admin");

        assertEquals(token1, token2);
    }
}
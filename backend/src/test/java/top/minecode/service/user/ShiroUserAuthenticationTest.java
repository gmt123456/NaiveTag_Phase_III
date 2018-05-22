package top.minecode.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.Test;
import top.minecode.web.user.SignupCommand;

import java.net.Authenticator;

import static org.testng.Assert.*;

/**
 * Created on 2018/5/22.
 * Description:
 * @author Liao
 */
@ContextConfiguration("classpath:naive-context.xml")
public class ShiroUserAuthenticationTest extends AbstractTestNGSpringContextTests {

    private UserAuthenticationService shiroUserAuthentication;


    @Autowired
    @Qualifier("shiroAuthenticationService")
    public void setShiroUserAuthentication(ShiroUserAuthentication shiroUserAuthentication) {
        this.shiroUserAuthentication = shiroUserAuthentication;
    }

    @Test
    public void testSignUp() {
        SignupCommand signupCommand = new SignupCommand();
        signupCommand.setEmail("frog@mail.com");
        signupCommand.setName("liao");
        signupCommand.setPassword("123456789");
        signupCommand.setUserType("worker");

        shiroUserAuthentication.signup(signupCommand);
    }
}
package top.minecode.web.requester.info;

import com.google.gson.JsonParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import top.minecode.web.user.AuthenticationController;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:naive*")
public class RequesterInfoControllerTest {

    private RequesterInfoController controller;
    private AuthenticationController authenticationController;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller, authenticationController).build();
    }

    @Autowired
    public void setController(RequesterInfoController controller) {
        this.controller = controller;
    }

    @Autowired
    public void setAuthenticationController(AuthenticationController authenticationController) {
        this.authenticationController = authenticationController;
    }

    @Test
    public void testGetMainInfo() throws Exception {
        // Get token
        String jsonResult = mockMvc.perform(post("/login")
                .param("email", "frogR@mail.com")
                .param("password", "123456789")
                .param("userType", "requester"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        JsonParser parser = new JsonParser();
        String token = parser.parse(jsonResult).getAsJsonObject().get("token").getAsString();

        mockMvc.perform(get("/requester/userInfo/main")
                .param("token",token))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
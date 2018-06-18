package top.minecode.web.user;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created on 2018/6/2.
 * Description:
 * @author Liao
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:naive*")
public class AuthenticationControllerTest {

    private AuthenticationController controller;
    private MockMvc mockMvc;
    private JsonParser parser = new JsonParser();

    @Autowired
    public void setController(AuthenticationController controller) {
        this.controller = controller;
    }

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testLogin1() throws Exception {
        String resultMsg = mockMvc.perform(post("/login")
                .param("email", "frogR@mail.com")
                .param("password", "123456789")
                .param("userType", "requester")
                .contentType(MediaType.ALL)
                .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonObject result = parser.parse(resultMsg).getAsJsonObject();
        assertEquals("success", result.get("status").getAsString());
    }

    @Test
    public void testLogin2() throws Exception {
        String resultMsg = mockMvc.perform(post("/login")
                .param("email", "frogR@mail.com")
                .param("password", "123456789")
                .param("userType", "worker")
                .contentType(MediaType.ALL)
                .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonObject result = parser.parse(resultMsg).getAsJsonObject();
        assertEquals("failure", result.get("status").getAsString());
        assertEquals("User type not match", result.get("message").getAsString());
    }

    @Test
    public void testLogin3() throws Exception {
        String resultMsg = mockMvc.perform(post("/login")
                .param("email", "frogR@mail.com")
                .param("password", "123")
                .param("userType", "worker")
                .contentType(MediaType.ALL)
                .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonObject result = parser.parse(resultMsg).getAsJsonObject();
        assertEquals("failure", result.get("status").getAsString());
        assertEquals("Invalid username or password", result.get("message").getAsString());
    }

    @Test
        public void signup() throws Exception {
        mockMvc.perform(post("/signup")
                .param("name", "frog")
                .param("email", "frogR@mail.com")
                .param("password", "123456789")
                .param("userType", "requester")
                .contentType(MediaType.ALL)
                .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
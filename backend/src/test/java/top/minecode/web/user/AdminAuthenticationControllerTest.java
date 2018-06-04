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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created on 2018/6/3.
 * Description:
 * @author Liao
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:naive*")
@WebAppConfiguration
public class AdminAuthenticationControllerTest {

    private AdminAuthenticationController controller;
    private MockMvc mockMvc;
    private String token;

    @Autowired
    public void setController(AdminAuthenticationController controller) {
        this.controller = controller;
    }

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        // Login admin
        JsonParser parser = new JsonParser();
        String result = mockMvc.perform(get("/inside/login")
                .param("username", "admin")
                .param("password", "admin")
                .accept(MediaType.ALL))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        JsonObject returnedJson = parser.parse(result).getAsJsonObject();

        token = returnedJson.get("token").getAsString();
    }

    @Test
    public void testCreateAdmin() throws Exception {
        mockMvc.perform(get("/inside/new/admin")
                .param("newAdminName", "frogAdmin1")
                .param("password", "admin")
                .param("token", token)
                .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateStaff() throws Exception {
        mockMvc.perform(get("/inside/new/staff")
                .param("email", "staffNaive")
                .param("password", "staff")
                .param("token", token)
                .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
package top.minecode.web.admin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.domain.user.admin.AdminAuthority;
import top.minecode.po.admin.AdminPO;
import top.minecode.service.util.Encryptor;
import top.minecode.web.user.AdminAuthenticationController;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created on 2018/6/2.
 * Description:
 * @author Liao
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:naive*")
@WebAppConfiguration
public class UserInformationControllerTest {

    private UserInformationController controller;
    private AdminAuthenticationController adminAuthenticationController;
    private MockMvc mockMvc;
    private Encryptor encryptor;
    private JsonParser parser = new JsonParser();

    @Autowired
    public void setEncryptor(Encryptor encryptor) {
        this.encryptor = encryptor;
    }

    @Autowired
    public void setAdminAuthenticationController(AdminAuthenticationController adminAuthenticationController) {
        this.adminAuthenticationController = adminAuthenticationController;
    }

    @Autowired
    public void setController(UserInformationController controller) {
        this.controller = controller;
    }

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller, adminAuthenticationController).build();
    }

    @Test
    public void getWorkers() throws Exception {
        String workers = mockMvc.perform(get("/admin/workers").accept(MediaType.ALL)
                .param("page", "1")
                .param("pageSize", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertNotNull(workers);
        System.out.println(workers);
    }

    @Test
    public void getRequester() throws Exception {
        String requester = mockMvc.perform(get("/admin/requester").accept(MediaType.ALL)
                .param("page", "1")
                .param("pageSize", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertNotNull(requester);
        System.out.println(requester);
    }

    @Test
    public void searchUser() throws Exception {
        String workers = mockMvc.perform(get("/admin/searchUser")
                .param("key", "frog")
                .param("page", "1")
                .param("pageSize", "2")
                .param("userType", "requester")
                .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertNotNull(workers);
        System.out.println(workers);

        mockMvc.perform(get("/admin/searchUser")
                .param("key", "naive")
                .param("page", "1")
                .param("pageSize", "2")
                .param("userType", "worker")
                .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void changePwd() throws Exception {
        String result = mockMvc.perform(get("/inside/login")
                .param("username", "admin")
                .param("password", "admin")
                .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonObject jsonObject = parser.parse(result).getAsJsonObject();
        String token = jsonObject.get("token").getAsString();

        mockMvc.perform(get("/admin/modify/password")
                .param("token", token)
                .param("email", "frogR@mail.com")
                .param("newPassword", "123456789")
                .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void changeDollars() throws Exception {
        String result = mockMvc.perform(get("/inside/login")
                .param("username", "admin")
                .param("password", "admin")
                .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonObject jsonObject = parser.parse(result).getAsJsonObject();
        String token = jsonObject.get("token").getAsString();

        mockMvc.perform(get("/admin/modify/dollars")
                .param("token", token)
                .param("email", "frogR@mail.com")
                .param("dollars", "-1000")
                .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
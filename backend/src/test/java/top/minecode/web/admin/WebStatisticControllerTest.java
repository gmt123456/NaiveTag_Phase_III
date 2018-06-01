package top.minecode.web.admin;

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
 * Created on 2018/6/1.
 * Description:
 * @author Liao
 */
@ContextConfiguration(locations = {"classpath*:naive*"})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class WebStatisticControllerTest {

    private WebStatisticController statisticController;
    private MockMvc mockMvc;

    @Autowired
    public void setStatisticController(WebStatisticController statisticController) {
        this.statisticController = statisticController;
    }

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(statisticController).build();
    }

    @Test
    public void activeUsersStatistic() throws Exception {
        String result = mockMvc.perform(get("/admin/statistic/activeUser")
                .contentType(MediaType.ALL)
                .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void totalUsersStatistic() throws Exception {
        String result = mockMvc.perform(get("/admin/statistic/signUpUser")
                .contentType(MediaType.ALL)
                .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void tasksStatistic() throws Exception {
        String result = mockMvc.perform(get("/admin/statistic/tasks")
                .contentType(MediaType.ALL)
                .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }
}
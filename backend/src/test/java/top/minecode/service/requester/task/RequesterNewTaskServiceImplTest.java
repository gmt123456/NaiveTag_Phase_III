package top.minecode.service.requester.task;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import top.minecode.web.requester.task.RequesterNewTaskController;
import top.minecode.web.user.AuthenticationController;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created on 2018/5/27.
 * Description:
 * @author Liao
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:naive*"})
@WebAppConfiguration
public class RequesterNewTaskServiceImplTest {

    private static final String CLASS_PATH = RequesterNewTaskServiceImplTest.class.getResource("/").getPath();

    private JsonParser parser = new JsonParser();
    private RequesterNewTaskController controller;
    private AuthenticationController authenticationController;

    private MockMvc mockMvc;
    private String loginToken;

    @Autowired
    public void setAuthenticationController(AuthenticationController authenticationController) {
        this.authenticationController = authenticationController;
    }

    @Autowired
    public void setController(RequesterNewTaskController controller) {
        this.controller = controller;
    }


    @Before
    public void setUp() throws Exception {

        FormattingConversionService formattingConversionService = new FormattingConversionService();
        formattingConversionService.addFormatter(new Formatter<LocalDate>() {
            @Override
            public LocalDate parse(String s, Locale locale) throws ParseException {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return LocalDate.parse(s, formatter);
            }

            @Override
            public String print(LocalDate localDate, Locale locale) {
                return localDate.toString();
            }
        });

        mockMvc = MockMvcBuilders.standaloneSetup(controller, authenticationController)
                .setConversionService(formattingConversionService).build();

        // Login
        String result = mockMvc.perform(get("/login")
                .param("email", "frogR@mail.com")
                .param("password", "123456789")
                .contentType(MediaType.ALL).accept(MediaType.ALL))
                .andReturn().getResponse().getContentAsString();


        loginToken = parser.parse(result).getAsJsonObject().get("token").getAsString();
        System.out.println(loginToken);
    }

    @Test
    @Ignore
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

    @Test
    public void testCreateAndPay() throws Exception {
        JsonObject object = parser.parse(new InputStreamReader(
                new FileInputStream(new File(CLASS_PATH, "newtask.json")))).getAsJsonObject();

        // Create multipart file
        InputStream inputStream = new FileInputStream("C:\\Users\\liao\\Documents" +
                "\\NaiveTag_Phase_III\\backend\\src\\main\\webapp\\WEB-INF" +
                "\\defaultData\\AnimeData.zip");
        MockMultipartFile data = new MockMultipartFile("dataset", "Anime.zip", MediaType.ALL_VALUE, inputStream);

        // Create request
        MockMultipartHttpServletRequestBuilder builder = fileUpload("/requester/new/taskOrder").file(data);
        setSpecificTasks(builder, object);
        setTags(builder, object);
        setOthers(builder, object);

        String result = mockMvc.perform(builder
                .param("token", loginToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        String orderId = parser.parse(result).getAsJsonObject().get("orderId").getAsString();

        String payResult = mockMvc.perform(get("/requester/new/pay")
                .param("token", loginToken)
                .param("orderId", orderId)
                .param("dollars", "10000")
                .param("advertisementDollars", "8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        System.out.println(payResult);
    }


    private void setSpecificTasks(MockHttpServletRequestBuilder builder, JsonObject object) {
        JsonArray tasks = object.get("tasks").getAsJsonArray();
        int i = 0;
        for (JsonElement element : tasks) {
            String paramName = String.format("tasks[%d]", i++);
            JsonObject task = element.getAsJsonObject();
            builder.param(paramName + ".type", task.get("type").getAsString())
                    .param(paramName + ".description", task.get("description").getAsString());

            JsonArray labels = task.get("labels").getAsJsonArray();
            for (JsonElement label : labels) {
                builder.param(paramName + ".labels", label.getAsString());
            }
        }
    }

    private void setTags(MockHttpServletRequestBuilder builder, JsonObject object) {
        for (JsonElement element : object.get("tags").getAsJsonArray()) {
            builder.param("tags", element.getAsString());
        }
    }

    private void setOthers(MockHttpServletRequestBuilder builder, JsonObject object) {
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            if (entry.getKey().equals("tasks") || entry.getKey().equals("tags"))
                continue;

            builder.param(entry.getKey(), entry.getValue().getAsString());
        }
    }
}
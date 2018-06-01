package top.minecode.domain.statistic;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created on 2018/6/1.
 * Description:
 * @author Liao
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:naive*")
public class ChartDataTest {

    private Gson gson;

    @Autowired
    public void setGson(Gson gson) {
        this.gson = gson;
    }

    @Test
    public void test1() {
        ChartData chartData = new ChartData();
        chartData.addVector("userTime", Arrays.asList("2018-1-1", "2018-1-2", "2018-1-3"));
        chartData.addVector("userData", Arrays.asList(123, 123, 123, 321));

        String expected = "{\"userTime\":[\"2018-1-1\",\"2018-1-2\",\"2018-1-3\"],\"userData\":[123,123,123,321]}";
        assertEquals(expected, gson.toJson(chartData));
    }
}
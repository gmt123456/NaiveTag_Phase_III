package top.minecode.service.statistic;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.minecode.dao.utils.GsonFactory;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:naive*")
public class StatisticServiceImplTest {

    private StatisticService statisticService;
    private Gson gson;

    @Autowired
    public void setGson(Gson gson) {
        this.gson = gson;
    }

    @Autowired
    public void setStatisticService(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @Test
    public void getWorkerEvaluation() {
        System.out.println(gson.toJson(statisticService.getWorkerEvaluation()));
    }

    @Test
    public void getTaskEvaluation() {
        System.out.println(gson.toJson(statisticService.getTaskEvaluation()));
    }

    @Test
    public void getDollarsStatistics() {
        System.out.println(gson.toJson(statisticService.getDollarStatistic()));
    }
}
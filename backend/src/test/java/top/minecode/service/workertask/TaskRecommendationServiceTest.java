package top.minecode.service.workertask;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:naive*")
public class TaskRecommendationServiceTest {

    private TaskRecommendationService recommendationService;

    @Autowired
    public void setRecommendationService(TaskRecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @Test
    public void getTaskRecommendations() {
        System.out.println(recommendationService.getTaskRecommendations("workerA@mail.com"));
    }
}
package top.minecode.service.statistic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.minecode.dao.statistic.WebStatisticDao;
import top.minecode.domain.statistic.ChartData;

/**
 * Created on 2018/6/1.
 * Description:
 * @author Liao
 */
@Service("statisticServiceImpl")
public class StatisticServiceImpl implements StatisticService {

    private WebStatisticDao statisticDao;

    @Autowired
    public void setStatisticDao(WebStatisticDao statisticDao) {
        this.statisticDao = statisticDao;
    }

    @Override
    public ChartData getActiveUserStatistic() {
        return statisticDao.getActiveUserData();
    }

    @Override
    public ChartData getSignUpUserStatistic() {
        return statisticDao.getSignUpStatistic();
    }

    @Override
    public ChartData getTasksStatistic() {
        return statisticDao.getTasksData();
    }
}

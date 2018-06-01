package top.minecode.service.statistic;

import org.springframework.stereotype.Service;
import top.minecode.domain.statistic.ChartData;

/**
 * Created on 2018/6/1.
 * Description:
 * @author Liao
 */
@Service("statisticServiceImpl")
public class StatisticServiceImpl implements StatisticService {

    @Override
    public ChartData getActiveUserData() {
        return null;
    }

    @Override
    public ChartData getTotalUsersData() {
        return null;
    }

    @Override
    public ChartData getTasksData() {
        return null;
    }
}

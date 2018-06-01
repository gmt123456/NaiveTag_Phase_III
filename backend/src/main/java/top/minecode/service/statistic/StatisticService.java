package top.minecode.service.statistic;

import top.minecode.domain.statistic.ChartData;

/**
 * Created on 2018/6/1.
 * Description:
 * @author Liao
 */
public interface StatisticService {

    ChartData getActiveUserData();

    ChartData getTotalUsersData();

    ChartData getTasksData();
}

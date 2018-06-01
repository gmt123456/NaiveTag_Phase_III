package top.minecode.dao.statistic;

import top.minecode.domain.statistic.ChartData;

/**
 * Created on 2018/6/1.
 * Description:
 * @author Liao
 */
public interface WebStatisticDao {

    ChartData getActiveUserData();

    ChartData getSignUpStatistic();

    ChartData getTasksData();
}

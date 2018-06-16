package top.minecode.dao.statistic;

import top.minecode.domain.statistic.ChartData;
import top.minecode.domain.statistic.RequesterTaskData;

import java.util.List;

/**
 * Created on 2018/6/1.
 * Description:
 * @author Liao
 */
public interface WebStatisticDao {

    ChartData getActiveUserData();

    ChartData getSignUpStatistic();

    ChartData getTasksData();

    ChartData getWorkerAbilityData(String email);

    List<RequesterTaskData> getRequesterTaskData(String email);
}

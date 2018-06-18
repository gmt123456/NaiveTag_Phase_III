package top.minecode.dao.statistic;

import top.minecode.domain.statistic.ChartData;
import top.minecode.domain.statistic.ParticipationData;
import top.minecode.po.worker.FinishedTaskParticipationPO;

import java.util.List;
import java.util.Map;

/**
 * Created on 2018/6/1.
 * Description:
 * @author Liao
 */
public interface WebStatisticDao {

    ChartData getActiveUserData();

    ChartData getSignUpStatistic();

    List<ParticipationData> getParticipationData();

    ChartData getTasksData();

    Map<Integer, List<FinishedTaskParticipationPO>> getFinishedTaskParticipationData();
}

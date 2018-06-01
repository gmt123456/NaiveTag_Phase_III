package top.minecode.dao.statistic;

import org.springframework.stereotype.Repository;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.domain.statistic.ChartData;
import top.minecode.domain.user.UserType;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created on 2018/6/1.
 * Description:
 * @author Liao
 */
@Repository("webStatisticDaoImpl")
public class WebStatisticDaoImpl implements WebStatisticDao {

    private static final String TIME = "time";
    private static final String USER_TYPE = "type";
    private static final String COUNT = "number";
    private static final String TOTAL_DATA = "totalData";
    private static final String WORKER_DATA = "workerData";
    private static final String REQUESTER_DATA = "requesterData";

    @Override
    public ChartData getActiveUserData() {
        // Collect total users' statistic
        String hql = "select new map (t.loginTime as time, t.userType as type, count(t) as number)" +
                " from LoginLogPO t group by cast(t.loginTime as date), t.userType order by cast(t.loginTime as date)";
        List<Map> rawData = CommonOperation.executeSQL(Map.class, hql);

        return processRawData(rawData, this::timestampToLocalDate);
    }

    @Override
    public ChartData getSignUpStatistic() {
        String hql = "select new map (t.registerDate as time, t.userType as type, count(t) as number)" +
                " from RegisterLogPO t group by cast(t.registerDate as date), t.userType order by cast(t.registerDate as date)";
        List<Map> rawData = CommonOperation.executeSQL(Map.class, hql);

        return processRawData(rawData, d -> ((Date) d).toLocalDate());
    }

    @Override
    public ChartData getTasksData() {
        String hql = "select new map (t.registerDate as time, t.userType as type, count(t) as number)" +
                " from RegisterLogPO t group by cast(t.registerDate as date), t.userType order by cast(t.registerDate as date)";
        return null;
    }

    private ChartData processRawData(List<Map> rawData, Function<Object, LocalDate> localDateFunction) {

        ChartData chartData = new ChartData();

        if (rawData.isEmpty()) {
            chartData.addEmptyVector(TIME);
            chartData.addEmptyVector(TOTAL_DATA);
            chartData.addEmptyVector(WORKER_DATA);
            chartData.addEmptyVector(REQUESTER_DATA);
            return chartData;
        }

        // Get time interval
        LocalDate start = localDateFunction.apply(rawData.get(0).get(TIME));
        LocalDate end = LocalDate.now().plusDays(1);
        List<LocalDate> dateInterval = Stream.iterate(start, d -> d.plusDays(1))
                .limit(ChronoUnit.DAYS.between(start, end))
                .collect(Collectors.toList());

        // Collect data
        int dateIntervalPointer = 0;
        List<Integer> totalValue = zeros(dateInterval.size());
        List<Integer> workerValue = zeros(dateInterval.size());
        List<Integer> requesterValue = zeros(dateInterval.size());
        for (Map dict : rawData) {
            LocalDate currDicTime = localDateFunction.apply(dict.get(TIME));

            LocalDate intervalTime = dateInterval.get(dateIntervalPointer);
            while (!intervalTime.isEqual(currDicTime)) {
                intervalTime = dateInterval.get(++dateIntervalPointer);
            }

            // Set values
            UserType userType = (UserType) dict.get(USER_TYPE);
            int count = ((Long) dict.get(COUNT)).intValue();

            if (userType == UserType.WORKER) {
                workerValue.set(dateIntervalPointer, count);
            } else if (userType == UserType.REQUESTER)
                requesterValue.set(dateIntervalPointer, count);

            totalValue.set(dateIntervalPointer, totalValue.get(dateIntervalPointer) + count);
        }

        // Get time list
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<String> datesString = dateInterval.stream().map(formatter::format).collect(Collectors.toList());

        chartData.addVector(TIME, datesString);
        chartData.addVector(TOTAL_DATA, totalValue);
        chartData.addVector(WORKER_DATA, workerValue);
        chartData.addVector(REQUESTER_DATA, requesterValue);
        return chartData;
    }

    private LocalDate timestampToLocalDate(Object timestamp) {
        java.util.Date date = (java.util.Date) timestamp;
        ZoneId zoneId = ZoneId.systemDefault();
        return date.toInstant().atZone(zoneId).toLocalDate();
    }

    private List<Integer> zeros(int size) {
        return new ArrayList<>(Collections.nCopies(size, 0));
    }
}

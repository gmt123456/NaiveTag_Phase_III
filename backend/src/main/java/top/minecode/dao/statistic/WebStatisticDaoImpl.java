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
    private static final String DONE_NUMBER = "doneNumber";
    private static final String COMMIT_NUMBER = "commitNumber";
    private static final String RELEASE_NUMBER = "releaseNumber";

    @Override
    public ChartData getActiveUserData() {
        // Collect total users' statistic
        String hql = "select new map (cast(t.loginTime as date) as time, t.userType as type, count(t) as number)" +
                " from LoginLogPO t group by cast(t.loginTime as date), t.userType order by cast(t.loginTime as date)";
        List<Map> rawData = CommonOperation.executeSQL(Map.class, hql);

        return processUserRawData(rawData, d -> ((Date) d).toLocalDate());
    }

    @Override
    public ChartData getSignUpStatistic() {
        String hql = "select new map (cast(t.registerDate as date) as time, t.userType as type, count(t) as number)" +
                " from RegisterLogPO t group by cast(t.registerDate as date), t.userType order by cast(t.registerDate as date)";
        List<Map> rawData = CommonOperation.executeSQL(Map.class, hql);

        return processUserRawData(rawData, d -> ((Date) d).toLocalDate());
    }

    @Override
    public ChartData getTasksData() {
        String completeTaskHql = "select new map(cast(t.accomplishDate as date) as time, count(t) as number) " +
                " from TaskAccomplishmentLogPO t group by cast(t.accomplishDate as date) order" +
                " by cast(t.accomplishDate as date)";

        String commitTaskHql = "select new map(cast(t.commitTime as date) as time, count(t) as number) " +
                " from TaskCommitmentLogPO t group by cast(t.commitTime as date) order by " +
                " cast(t.commitTime as date)";

        String releaseTaskHql = "select new map(cast(t.releaseDate as date) as time, count(t) as number) " +
                " from ReleaseTaskLogPO t group by cast(t.releaseDate as date) order by " +
                " cast(t.releaseDate as date)";

        List<Map> completeTaskRawData = CommonOperation.executeSQL(Map.class, completeTaskHql);
        List<Map> commitTaskRawData = CommonOperation.executeSQL(Map.class, commitTaskHql);
        List<Map> releaseTaskRawData = CommonOperation.executeSQL(Map.class, releaseTaskHql);

        LocalDate start = getMinDate(getTaskRawDataFirstDate(commitTaskRawData),
                getTaskRawDataFirstDate(completeTaskRawData), getTaskRawDataFirstDate(releaseTaskRawData));
        List<LocalDate> dateInterval = getToNowInterval(start);

        ChartData chartData = new ChartData();
        chartData.addVector(TIME, formatLocalDateStrings(dateInterval));
        processTaskRawData(completeTaskRawData, DONE_NUMBER, chartData, dateInterval);
        processTaskRawData(commitTaskRawData, COMMIT_NUMBER, chartData, dateInterval);
        processTaskRawData(releaseTaskRawData, RELEASE_NUMBER, chartData, dateInterval);

        return chartData;
    }

    private LocalDate getTaskRawDataFirstDate(List<Map> taskRawData) {
        return taskRawData.isEmpty() ? LocalDate.now() : sqlDateToLocalDate(taskRawData.get(0).get(TIME));
    }

    private LocalDate getMinDate(LocalDate... localDates) {
        // If not empty, find the minimum date
        if (localDates != null) {
            LocalDate min = LocalDate.MAX;
            for (LocalDate curr : localDates) {
                if (curr.isBefore(min))
                    min = curr;
            }
            return min;
        }

        // If empty, just return now
        return LocalDate.now();
    }

    private void processTaskRawData(List<Map> rawData, String dataTypeName, ChartData chartData, List<LocalDate> dateInterval) {
        if (rawData.isEmpty()) {
            chartData.addVector(dataTypeName, zeros(dateInterval.size()));
            return;
        }

        // Collect data
        int intervalPointer = 0;
        List<Integer> value = zeros(dateInterval.size());
        for (Map dict : rawData) {
            LocalDate currDicTime = sqlDateToLocalDate(dict.get(TIME));

            LocalDate intervalTime = dateInterval.get(intervalPointer);
            while (!intervalTime.isEqual(currDicTime)) {
                intervalTime = dateInterval.get(++intervalPointer);
            }

            // Set counts
            int count = ((Long) dict.get(COUNT)).intValue();
            value.set(intervalPointer, count);
        }

        chartData.addVector(dataTypeName, value);
    }

    private ChartData processUserRawData(List<Map> rawData, Function<Object, LocalDate> localDateFunction) {


        if (rawData.isEmpty()) {
            return emptyChart(TIME, TOTAL_DATA, WORKER_DATA, REQUESTER_DATA);
        }

        // Get time interval
        LocalDate start = localDateFunction.apply(rawData.get(0).get(TIME));
        List<LocalDate> dateInterval = getToNowInterval(start);

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
            } else if (userType == UserType.REQUESTER) {
                requesterValue.set(dateIntervalPointer, count);
            }

            totalValue.set(dateIntervalPointer, totalValue.get(dateIntervalPointer) + count);
        }

        // Format LocalDate
        List<String> datesString = formatLocalDateStrings(dateInterval);

        ChartData chartData = new ChartData();
        chartData.addVector(TIME, datesString);
        chartData.addVector(TOTAL_DATA, totalValue);
        chartData.addVector(WORKER_DATA, workerValue);
        chartData.addVector(REQUESTER_DATA, requesterValue);
        return chartData;
    }

    private LocalDate sqlDateToLocalDate(Object localDate) {
        java.sql.Date date = (java.sql.Date) localDate;
        return date.toLocalDate();
    }

    private List<Integer> zeros(int size) {
        return new ArrayList<>(Collections.nCopies(size, 0));
    }

    private ChartData emptyChart(String... headers) {
        ChartData chartData = new ChartData();
        if (headers != null) {
            for (String s : headers)
                chartData.addEmptyVector(s);
        }

        return chartData;
    }

    private List<LocalDate> getToNowInterval(LocalDate start) {
        LocalDate end = LocalDate.now().plusDays(1);
        return Stream.iterate(start, d -> d.plusDays(1))
                .limit(ChronoUnit.DAYS.between(start, end))
                .collect(Collectors.toList());
    }

    private List<String> formatLocalDateStrings(List<LocalDate> localDates) {
        // Get time list
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDates.stream().map(formatter::format).collect(Collectors.toList());
    }
}

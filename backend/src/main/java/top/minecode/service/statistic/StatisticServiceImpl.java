package top.minecode.service.statistic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.minecode.dao.auto.WorkerVectorDao;
import top.minecode.dao.log.AccountLogDao;
import top.minecode.dao.statistic.WebStatisticDao;
import top.minecode.dao.worker.WorkerInfoDao;
import top.minecode.dao.workertask.TaskDao;
import top.minecode.domain.statistic.ChartData;
import top.minecode.domain.statistic.ParticipationData;
import top.minecode.domain.task.TaskState;
import top.minecode.domain.task.TaskType;
import top.minecode.domain.user.worker.Division;
import top.minecode.domain.utils.DateUtils;
import top.minecode.domain.utils.VectorHelper;
import top.minecode.po.auto.WorkerVectorPO;
import top.minecode.po.log.RequesterAccountLogPO;
import top.minecode.po.log.RequesterAccountLogPO.ChangeType;
import top.minecode.po.log.WorkerAccountLogPO;
import top.minecode.po.log.WorkerAccountLogPO.WorkerAccountChangeType;
import top.minecode.po.task.TaskPO;
import top.minecode.po.worker.FinishedTaskParticipationPO;
import top.minecode.po.worker.WorkerPO;

import java.time.LocalDate;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created on 2018/6/1.
 * Description:
 *
 * @author Liao
 */
@Service("statisticServiceImpl")
public class StatisticServiceImpl implements StatisticService {

    private WebStatisticDao statisticDao;
    private WorkerInfoDao workerInfoDao;
    private WorkerVectorDao workerVectorDao;
    private TaskDao taskDao;
    private AccountLogDao accountLogDao;

    @Autowired
    public void setAccountLogDao(AccountLogDao accountLogDao) {
        this.accountLogDao = accountLogDao;
    }

    @Autowired
    public void setWorkerVectorDao(WorkerVectorDao workerVectorDao) {
        this.workerVectorDao = workerVectorDao;
    }

    @Autowired
    public void setTaskDao(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Autowired
    public void setWorkerInfoDao(WorkerInfoDao workerInfoDao) {
        this.workerInfoDao = workerInfoDao;
    }

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
    public ChartData getWorkerDistribution() {
        // Get map(division, number)
        Map<Division, Long> divisions = workerInfoDao.getAll()
                .stream().map(worker -> Division.convert(worker.getScore()))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // Calculate percent and add them to chart
        ChartData result = new ChartData();
        setPercentFrom(divisions, result);
        return result;
    }

    @Override
    public ChartData getTaskPieChart() {
        ChartData result = new ChartData();
        Map<TaskType, Long> data = taskDao.getAll().stream()
                .flatMap(task -> task.getSpecificTasks().keySet().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        setPercentFrom(data, result);
        return result;
    }

    public ChartData getWorkerEvaluation() {
        ChartData result = new ChartData();

        List<ParticipationData> participationStatistics = statisticDao.getParticipationData();

        Map<Division, List<ParticipationData>> divisionMap =
                participationStatistics.stream()
                        .collect(Collectors.groupingBy(s -> getDivision(s.getWorker())));
        fillDivisions(divisionMap, ArrayList::new);

        // Get tag error
        List<Double> tagError = divisionMap.entrySet().stream()
                .sorted(Comparator.comparing(Entry::getKey))
                .map(e -> getExpectedTagError(filterNotEvaluated(e.getValue())))
                .collect(Collectors.toList());
        result.addVector("tagError", tagError);

        // Add speed statistics
        Map<String, Double> speedMap = workerInfoDao.getWorkerSpeedInPictures();
        Map<Division, List<Double>> divisionSpeedMap = new HashMap<>();
        for (Entry<String, Double> entry : speedMap.entrySet()) {
            Division d = getDivision(entry.getKey());
            if (divisionSpeedMap.get(d) == null) {
                List<Double> speeds = new ArrayList<>();
                speeds.add(entry.getValue());
                divisionSpeedMap.put(d, speeds);
            }
            else {
                divisionSpeedMap.get(d).add(entry.getValue());
            }
        }
        fillDivisions(divisionSpeedMap, ArrayList::new);

        List<Double> speed = divisionSpeedMap.entrySet().stream()
                .sorted(Comparator.comparing(Entry::getKey))
                .map(e -> e.getValue().stream().mapToDouble(t -> t).average().orElse(0.))
                .collect(Collectors.toList());
        result.addVector("speed", speed);

        // Add worker ability statistics
        List<WorkerVectorPO> workerVectors = workerVectorDao.getAll();
        Map<Division, List<Double>> abilityMap = new HashMap<>();
        for (WorkerVectorPO v : workerVectors) {
            WorkerPO workerPO = workerInfoDao.getWorkerPOByEmail(v.getEmail());
            Division workerDivision = getDivision(workerPO.getEmail());
            if (abilityMap.get(workerDivision) == null) {
                List<Double> list = new ArrayList<>();
                list.add(VectorHelper.norm(v.getVector()));
                abilityMap.put(workerDivision, list);
            }
            else {
                abilityMap.get(workerDivision).add(VectorHelper.norm(v.getVector()));
            }
        }
        fillDivisions(abilityMap, ArrayList::new);

        List<Double> ability = abilityMap.entrySet().stream()
                .sorted(Comparator.comparing(Entry::getKey))
                .map(e -> e.getValue().stream().mapToDouble(t -> t).average().orElse(0.))
                .collect(Collectors.toList());
        result.addVector("ability", ability);

        // Accepted tasks statistics
        List<long[]> acceptedTasks = divisionMap.entrySet().stream()
                .sorted(Comparator.comparing(Entry::getKey))
                .map(e -> countAcceptTimes(e.getValue()))
                .collect(Collectors.toList());
        result.addVector("acceptedTasks", acceptedTasks);

        return result;
    }

    private List<ParticipationData> filterNotEvaluated(List<ParticipationData> statistics) {
        return statistics.stream().filter(ParticipationData::isEvaluated).collect(Collectors.toList());
    }

    private <T> void fillDivisions(Map<Division, T> divisionMap, Supplier<T> t) {
        if (divisionMap.size() < Division.values().length) {
            for (Division d : Division.values()) {
                if (!divisionMap.containsKey(d))
                    divisionMap.put(d, t.get());
            }
        }
    }

    private <V> void fillTaskTypes(Map<TaskType, V> map, Supplier<V> v) {
        if (map.size() < TaskType.values().length) {
            for (TaskType type : TaskType.values()) {
                if (!map.containsKey(type))
                    map.put(type, v.get());
            }
        }
    }

    private long[] countAcceptTimes(List<ParticipationData> statistics) {
        long[] result = new long[TaskType.values().length];
        Arrays.fill(result, 0L);

        statistics.forEach(e -> result[e.getTaskType().ordinal()]++);
        return result;
    }

    private double getExpectedTagError(List<ParticipationData> statistics) {
        return statistics.stream()
                .mapToDouble(ParticipationData::getErrorRate)
                .average().orElse(0.);
    }

    private Division getDivision(String email) {
        WorkerPO workerPO = workerInfoDao.getWorkerPOByEmail(email);
        return Division.convert(workerPO.getScore());
    }

    private <T extends Enum> void setPercentFrom(Map<T, Long> data, ChartData chart) {
        long totalNum = data.values().stream().mapToLong(e -> e).sum();
        for (Entry<T, Long> entry : data.entrySet()) {
            chart.addField(entry.getKey().toString(), entry.getValue() * 1. / totalNum);
        }
    }

    @Override
    public ChartData getTaskEvaluation() {
        ChartData result = new ChartData();

        List<ParticipationData> participationStatistics = statisticDao.getParticipationData();

        Map<TaskType, List<ParticipationData>> typeMap =
                participationStatistics.stream()
                        .collect(Collectors.groupingBy(ParticipationData::getTaskType));
        fillTaskTypes(typeMap, ArrayList::new);

        // TagError statistic
        List<Double> tagError = typeMap.entrySet().stream()
                .sorted(Comparator.comparing(Entry::getKey))
                .map(e -> getExpectedTagError(filterNotEvaluated(e.getValue())))
                .collect(Collectors.toList());


        result.addVector("tagError", tagError);

        // Speed statistic
        Map<TaskType, Double> typeSpeedMap = workerInfoDao.getWorkerSpeedInPicturesByTaskType();
        List<Double> speed = typeSpeedMap.entrySet().stream()
                .sorted(Comparator.comparing(Entry::getKey))
                .map(Entry::getValue)
                .collect(Collectors.toList());

        result.addVector("speed", speed);

        // Participation statistics
        List<Integer> participantsNum = typeMap.entrySet().stream()
                .sorted(Comparator.comparing(Entry::getKey))
                .map(e -> e.getValue().size())
                .collect(Collectors.toList());

        result.addVector("participantsNum", participantsNum);

        return result;
    }

    @Override
    public ChartData getDollarStatistic() {
        ChartData result = new ChartData();
        // Timeline
        List<LocalDate> timeline = DateUtils.getToNowInterval();
        result.addVector("date", DateUtils.formatLocalDateStrings(timeline));

        // Recharge
        List<RequesterAccountLogPO> rechargeLogs = accountLogDao.getRequesterAccountLogs(ChangeType.RECHARGE);
        result.addVector("recharge", processAccountLogs(rechargeLogs, timeline));

        // Prize
        List<RequesterAccountLogPO> prizeLogs = accountLogDao.getRequesterAccountLogs(ChangeType.RELEASE_TASK);
        result.addVector("prize", processAccountLogs(prizeLogs, timeline));

        // Pay worker
        List<WorkerAccountLogPO> payWorkerLogs = accountLogDao.getWorkerAccountLogs(WorkerAccountChangeType.PAY);
        result.addVector("payWorker", processWorkerAccountLogs(payWorkerLogs, timeline));

        // Advertisement
        List<RequesterAccountLogPO> advertisementLogs = accountLogDao.getRequesterAccountLogs(ChangeType.ADVERTISEMENT);
        result.addVector("advertisement", processAccountLogs(advertisementLogs, timeline));

        // Expected prize for each task
        List<TaskPO> tasks = taskDao.getAll();
        double expectedPrize = tasks.stream().mapToDouble(TaskPO::getTotalDollars).average().orElse(0.);
        result.addField("expectedPrize", expectedPrize);

        // Expected dollars for workers get from each task
        Map<Integer, List<FinishedTaskParticipationPO>> taskParticipationMap =
                statisticDao.getFinishedTaskParticipationData();

        double expectedPayWorker = taskParticipationMap.values().stream()
                .mapToDouble(this::payWorkerAvg).average().orElse(0.);
        result.addField("expectedPayWorker", expectedPayWorker);

        // Expected adRate
        double expectedAdRate = tasks.stream().mapToDouble(TaskPO::getAdRate).average().orElse(0.);
        result.addField("expectedAdRate", expectedAdRate);

        // Expected pay back rate
        double expectedPayBackRate = tasks.stream().filter(t -> t.getTaskState() == TaskState.FINISHED)
                .mapToDouble(t -> 1 - t.getActualDollars() / t.getTotalDollars())
                .average().orElse(0.);
        result.addField("expectedPayBackRate", expectedPayBackRate);

        return result;
    }

    @Override
    public ChartData getTasksStatistic() {
        return statisticDao.getTasksData();
    }

    private double payWorkerAvg(List<FinishedTaskParticipationPO> data) {
        return data.stream().mapToDouble(FinishedTaskParticipationPO::getEarnedDollars).average().orElse(0.);
    }

    private <T> Map<T, List<ParticipationData>> groupParticipationStatistics(List<ParticipationData> data,
                                                                             Function<ParticipationData, T> key) {
        return data.stream().collect(Collectors.groupingBy(key));
    }

    private List<Double> processAccountLogs(List<RequesterAccountLogPO> logs, List<LocalDate> timeline) {
        Map<LocalDate, Double> rechargeMap = logs.stream()
                .collect(Collectors.groupingBy(
                        (RequesterAccountLogPO log) -> DateUtils.toLocalDate(log.getTime()),
                        Collectors.summingDouble(RequesterAccountLogPO::getDollars)));

        // Accumulate the money
        return processData(timeline, rechargeMap);
    }

    private List<Double> processWorkerAccountLogs(List<WorkerAccountLogPO> logs, List<LocalDate> timeline) {
        Map<LocalDate, Double> rechargeMap = logs.stream()
                .collect(Collectors.groupingBy(
                        (WorkerAccountLogPO log) -> DateUtils.toLocalDate(log.getTime()),
                        Collectors.summingDouble(WorkerAccountLogPO::getDollars)));

        // Accumulate the money
        return processData(timeline, rechargeMap);
    }

    private List<Double> processData(List<LocalDate> timeline, Map<LocalDate, Double> data) {
        List<Double> result = VectorHelper.zeros(timeline.size());

        // Get sorted entries
        List<Entry<LocalDate, Double>> entries = data.entrySet().stream()
                .sorted(Comparator.comparing(Entry::getKey)).collect(Collectors.toList());

        double accumulation = 0;
        int timeLinePointer = 0;
        for (Entry<LocalDate, Double> entry : entries) {
            LocalDate curr = entry.getKey();
            LocalDate timelineTime = timeline.get(timeLinePointer);

            while (timelineTime.isBefore(curr)) {
                result.set(timeLinePointer, accumulation);
                timelineTime = timeline.get(++timeLinePointer);
            }

            accumulation += Math.abs(entry.getValue());
        }

        // Set rest time
        do {
            result.set(timeLinePointer, accumulation);
            timeLinePointer++;
        } while (timeLinePointer < timeline.size());

        return result;
    }
}

package top.minecode.service.workertask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.minecode.dao.auto.TaskVectorDao;
import top.minecode.dao.auto.WorkerTasteDao;
import top.minecode.dao.auto.WorkerVectorDao;
import top.minecode.dao.worker.RankDao;
import top.minecode.dao.worker.WorkerInfoDao;
import top.minecode.dao.workertask.TaskDao;
import top.minecode.domain.task.FeatureVector;
import top.minecode.domain.task.Task;
import top.minecode.domain.task.TaskRequirement;
import top.minecode.domain.utils.VectorHelper;
import top.minecode.po.auto.TaskVectorPO;
import top.minecode.po.auto.WorkerTastePO;
import top.minecode.po.auto.WorkerVectorPO;
import top.minecode.po.task.TaskPO;
import top.minecode.po.worker.WorkerPO;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
@Service
public class TaskRecommendationService {

    private static final double RECOMMENDATION_THRESHOLD = 1.0;

    private TaskVectorDao taskVectorDao;
    private WorkerVectorDao workerVectorDao;
    private WorkerTasteDao tasteDao;
    private TaskDao taskDao;
    private WorkerInfoDao workerInfoDao;
    private RankDao rankDao;

    public WorkerInfoDao getWorkerInfoDao() {
        return workerInfoDao;
    }

    @Autowired
    public void setRankDao(RankDao rankDao) {
        this.rankDao = rankDao;
    }

    @Autowired
    public void setTasteDao(WorkerTasteDao tasteDao) {
        this.tasteDao = tasteDao;
    }

    @Autowired
    public void setWorkerInfoDao(WorkerInfoDao workerInfoDao) {
        this.workerInfoDao = workerInfoDao;
    }

    public TaskDao getTaskDao() {
        return taskDao;
    }

    @Autowired
    public void setTaskDao(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public WorkerVectorDao getWorkerVectorDao() {
        return workerVectorDao;
    }

    @Autowired
    public void setWorkerVectorDao(WorkerVectorDao workerVectorDao) {
        this.workerVectorDao = workerVectorDao;
    }


    @Autowired
    public void setTaskVectorDao(TaskVectorDao taskVectorDao) {
        this.taskVectorDao = taskVectorDao;
    }

    private List<TaskPO> rankTaskByAdRate() {
        List<TaskPO> allTasks = taskDao.getAll();
        allTasks.sort((e1, e2) -> Double.compare(e2.getAdRate(), e1.getAdRate()));
        return allTasks;
    }

    private List<Integer> rankTasksByUserFeature(String email) {
        List<TaskVectorPO> taskVectorPOS = taskVectorDao.getAll();
        WorkerVectorPO workerVectorPO = workerVectorDao.getVectorByEmail(email);
        taskVectorPOS.sort((e1, e2) -> Double.compare(VectorHelper.dot(e2.getVector(), workerVectorPO.getVector()),
                VectorHelper.dot(e1.getVector(), workerVectorPO.getVector())));
        return taskVectorPOS.stream().map(TaskVectorPO::getTaskId).collect(Collectors.toList());
    }

//    public List<Task> getTaskRecommendations(String email) {
//
//        int recommendAmount = 10; // 任务推荐的数量
//
//        WorkerPO workerPO = workerInfoDao.getWorkerPOByEmail(email);
//
//        Set<Integer> participatedTasks = new HashSet<>(workerPO.getParticipatedTasks());
//
//        // 根据推广费获得的任务
//        List<TaskPO> adTasks = rankTaskByAdRate().stream().filter(e -> !participatedTasks.contains(e.getId()))
//                .collect(Collectors.toList());
//        Map<Integer, TaskPO> id2Task = adTasks.stream().collect(Collectors.toMap(TaskPO::getId, e -> e));
//
//        // 根据用户能力获得的任务
//        List<Integer> taskIdsRankedByAbility = rankTasksByUserFeature(email).stream()
//                .filter(e -> !participatedTasks.contains(e))
//                .collect(Collectors.toList());
//
//        if (taskIdsRankedByAbility.size() <= recommendAmount) // 假如任务比较少，那么就直接按照推广费来返回
//            return adTasks.stream().map(Task::fromPO).collect(Collectors.toList());
//
//        // 目标任务的id集合
//        List<Integer> targets = new ArrayList<>();
//
//        // 否则就前3个是广告
//        List<Double> accumulatedAdRate = new ArrayList<>();
//
//        double accumulated = 0.0;
//        for (TaskPO adTask : adTasks) {
//            accumulated += adTask.getAdRate();
//            accumulatedAdRate.add(accumulated);
//        }
//
//        Random random = new Random();
//
//        while (targets.size() < 3) {
//            int bound = (int) (accumulated * 10000);
//            double number = random.nextInt(bound) * 1.0 / 10000;
//            int newId = -1;
//            for (int i = 0; i < accumulatedAdRate.size(); i++) {
//                if (accumulatedAdRate.get(i) >= number) {
//                    newId = adTasks.get(i).getId();
//                    break;
//                }
//            }
//            if (newId != -1 && !targets.contains(newId))
//                targets.add(newId);
//        }
//
//        while (targets.size() < recommendAmount) { // 排名第 k 的任务有 1/(k+1)的概率，如果一遍没搞满，再来一遍
//            for (int i = 0; i < taskIdsRankedByAbility.size(); i++) {
//                double temp = random.nextDouble();
//                if (temp <= 1.0 / (i + 1) && !targets.contains(taskIdsRankedByAbility.get(i))) {
//                    targets.add(taskIdsRankedByAbility.get(i));
//                    if (targets.size() == recommendAmount)
//                        break;
//                }
//            }
//        }
//
//        return targets.stream().map(id2Task::get).map(Task::fromPO).collect(Collectors.toList());
//    }

    public List<Task> getTaskRecommendations(String email) {

        List<TaskPO> tasks = taskDao.getAll();
        List<Integer> participatedTasks = workerInfoDao.getParticipatedTasks(email);

        List<FeatureVector<TaskPO>> unacceptedTasks = tasks.stream()
                .filter(taskPO -> !participatedTasks.contains(taskPO.getId()))
                .map(po -> new FeatureVector<>(po, TaskVectorPO.fromTaskPO(po).getVector()))
                .collect(Collectors.toList());

        Proportion proportion = getProportion(email);

        // Recommend by interest
        WorkerTastePO selfTastePO = tasteDao.get(email);
        Set<TaskPO> result = new HashSet<>(recommendBy(unacceptedTasks,
                v -> VectorHelper.dot(selfTastePO.getVector(), v.getVector()),
                proportion.getInterestNum()));

        unacceptedTasks = removeTask(unacceptedTasks, result);  // Avoid repeated

        // Recommend by quality
        WorkerVectorPO workerVectorPO = workerVectorDao.getVectorByEmail(email);
        result.addAll(recommendBy(unacceptedTasks,
                v -> VectorHelper.dot(workerVectorPO.getVector(), v.getVector()),
                proportion.getQualityNum()));

        unacceptedTasks = removeTask(unacceptedTasks, result);

        // Recommend by speed
        // Get all speed-first unaccepted tasks
        List<FeatureVector<TaskPO>> speedFirstTasks = unacceptedTasks.stream()
                .filter(v -> v.getIdentity().getRequirement() != TaskRequirement.SPEED)
                .collect(Collectors.toList());

        // The keyExtractor is f -> 10. which means sampling from a uniform distribution
        result.addAll(recommendBy(speedFirstTasks, f -> 10., proportion.getSpeedNum()));

        return result.stream()
                .sorted(Comparator.comparing(TaskPO::getAdRate).reversed())  // Ranking by adRate
                .map(Task::fromPO).collect(Collectors.toList());
    }

    private List<FeatureVector<TaskPO>> removeTask(List<FeatureVector<TaskPO>> unacceptedTasks,
                                                   Collection<TaskPO> toBeRemoved) {
        return unacceptedTasks.stream().filter(v -> !toBeRemoved.contains(v.getIdentity())).collect(Collectors.toList());
    }

    private List<TaskPO> recommendBy(List<FeatureVector<TaskPO>> unacceptedTasks,
                                     Function<FeatureVector<TaskPO>, Double> scoreFunction, int num) {
        // If the unaccepted tasks number is less than num,
        // then just sort these tasks by adRate and return
        int thresholdSize = (int) (unacceptedTasks.size() * RECOMMENDATION_THRESHOLD);
        if (thresholdSize <= num)
            return unacceptedTasks.stream().map(FeatureVector::getIdentity)
                    .sorted(Comparator.comparing(TaskPO::getAdRate))
                    .limit(num)
                    .collect(Collectors.toList());

        // Extract tasks to be sampled from by the value extracted by scoreFunction
        Comparator<FeatureVector<TaskPO>> descComparator = Comparator.comparing(scoreFunction).reversed();
        unacceptedTasks.sort(descComparator);
        unacceptedTasks.subList(0, thresholdSize);

        return samplingBy(unacceptedTasks, scoreFunction, num);
    }

    // The higher the score is the more likely the task is chosen
    private List<TaskPO> samplingBy(List<FeatureVector<TaskPO>> tasks,
                                    Function<FeatureVector<TaskPO>, Double> scoreFunction, int num) {
        // Choose these tasks by the rule that the higher the
        // task's rank is the more likely the task be chosen
        List<Double> valueSections = new ArrayList<>();
        double accumulation = 0.;
        for (FeatureVector<TaskPO> task : tasks) {
            double score = scoreFunction.apply(task) + 1;  // Plus one means a Laplace smoothing
            accumulation += score;
            valueSections.add(accumulation);
        }
        valueSections.add(accumulation);

        // Choosing from the section
        Random random = new Random();
        Set<TaskPO> result = new HashSet<>();
        do {
            double randomValue = random.nextDouble() * accumulation;
            for (int j = 0; j < valueSections.size(); j++) {
                double sectionLimit = valueSections.get(j);
                if (randomValue <= sectionLimit) {  // May also be '<'
                    result.add(tasks.get(j).getIdentity());
                    break;
                }
            }
        } while (result.size() != num);

        // Result will be sorted by adRate
        return result.stream()
                .sorted(Comparator.comparing(TaskPO::getAdRate).reversed())
                .collect(Collectors.toList());
    }

    private Proportion getProportion(String email) {
        double scoreRankRate = rankDao.getScoreRankRate(email);
        double speedRankRate = workerInfoDao.getWorkerSpeedRankRate(email);

        return new Proportion(speedRankRate, scoreRankRate);
    }

    private class Proportion {

        // Used in calculating proportion of each part
        // Original proportion is 4:3:3 (interest:quality:speed)
        private static final double QUALITY_LIMIT = 0.3;
        private static final double SPEED_LIMIT = 0.3;
        private static final int RECOMMENDATION_NUM = 5;

        private double speed;
        private double quality;

        /**
         * Calculate proportion of speed-first task and
         * quality-first task recommendation for a worker.
         * Note that if there's only one worker in our
         * system, this method will just treat him as the
         * last one and recommend tasks all by interest.
         * @param speedRankRate speed rank rate
         * @param qualityRankRate quality rank rate
         */
        private Proportion(double speedRankRate, double qualityRankRate) {
            this.speed = (1 - speedRankRate) * SPEED_LIMIT;
            this.quality = (1 - qualityRankRate) * QUALITY_LIMIT;
        }


        private int getInterestNum() {
            return RECOMMENDATION_NUM - getSpeedNum() - getQualityNum();
        }

        private int getSpeedNum() {
            return (int) speed * RECOMMENDATION_NUM;
        }

        private int getQualityNum() {
            return (int) quality * RECOMMENDATION_NUM;
        }
    }

}

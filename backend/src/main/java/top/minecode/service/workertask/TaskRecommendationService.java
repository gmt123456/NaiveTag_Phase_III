package top.minecode.service.workertask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.minecode.dao.auto.TaskVectorDao;
import top.minecode.dao.auto.WorkerTasteDao;
import top.minecode.dao.auto.WorkerVectorDao;
import top.minecode.dao.worker.WorkerInfoDao;
import top.minecode.dao.workertask.TaskDao;
import top.minecode.domain.task.FeatureVector;
import top.minecode.domain.task.Task;
import top.minecode.domain.utils.VectorHelper;
import top.minecode.po.auto.TaskVectorPO;
import top.minecode.po.auto.WorkerTastePO;
import top.minecode.po.auto.WorkerVectorPO;
import top.minecode.po.task.TaskPO;
import top.minecode.po.worker.WorkerPO;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created on 2018/5/19.
 * Description:
 * @author iznauy
 */
@Service
public class TaskRecommendationService {

    private static final int RECOMMENDATION_NUM = 10;

    private TaskVectorDao taskVectorDao;
    private WorkerVectorDao workerVectorDao;
    private WorkerTasteDao tasteDao;
    private TaskDao taskDao;
    private WorkerInfoDao workerInfoDao;

    public WorkerInfoDao getWorkerInfoDao() {
        return workerInfoDao;
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

    public TaskVectorDao getTaskVectorDao() {
        return taskVectorDao;
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

    public List<Task> getTaskRecommendations(String email) {

        int recommendAmount = 10; // 任务推荐的数量

        WorkerPO workerPO = workerInfoDao.getWorkerPOByEmail(email);

        Set<Integer> participatedTasks = new HashSet<>(workerPO.getParticipatedTasks());

        // 根据推广费获得的任务
        List<TaskPO> adTasks = rankTaskByAdRate().stream().filter(e -> !participatedTasks.contains(e.getId()))
                .collect(Collectors.toList());
        Map<Integer, TaskPO> id2Task = adTasks.stream().collect(Collectors.toMap(TaskPO::getId, e -> e));

        // 根据用户能力获得的任务
        List<Integer> taskIdsRankedByAbility = rankTasksByUserFeature(email).stream()
                .filter(e -> !participatedTasks.contains(e))
                .collect(Collectors.toList());

        if (taskIdsRankedByAbility.size() <= recommendAmount) // 假如任务比较少，那么就直接按照推广费来返回
            return adTasks.stream().map(Task::fromPO).collect(Collectors.toList());

        // 目标任务的id集合
        List<Integer> targets = new ArrayList<>();

        // 否则就前3个是广告
        List<Double> accumulatedAdRate = new ArrayList<>();

        double accumulated = 0.0;
        for (TaskPO adTask : adTasks) {
            accumulated += adTask.getAdRate();
            accumulatedAdRate.add(accumulated);
        }

        Random random = new Random();

        while (targets.size() < 3) {
            int bound = (int) (accumulated * 10000);
            double number = random.nextInt(bound) * 1.0 / 10000;
            int newId = -1;
            for (int i = 0; i < accumulatedAdRate.size(); i++) {
                if (accumulatedAdRate.get(i) >= number) {
                    newId = adTasks.get(i).getId();
                    break;
                }
            }
            if (newId != -1 && !targets.contains(newId))
                targets.add(newId);
        }

        while (targets.size() < recommendAmount) { // 排名第 k 的任务有 1/(k+1)的概率，如果一遍没搞满，再来一遍
            for (int i = 0; i < taskIdsRankedByAbility.size(); i++) {
                double temp = random.nextDouble();
                if (temp <= 1.0 / (i + 1) && !targets.contains(taskIdsRankedByAbility.get(i))) {
                    targets.add(taskIdsRankedByAbility.get(i));
                    if (targets.size() == recommendAmount)
                        break;
                }
            }
        }

        return targets.stream().map(id2Task::get).map(Task::fromPO).collect(Collectors.toList());
    }

    // New version recommendation
    private List<Task> recommendation(String email) {
        WorkerTastePO selfTastePO = tasteDao.get(email);
        List<TaskPO> tasks = taskDao.getAll();

        return null;
    }

    private List<Task> recommendate(String email) {

        WorkerPO worker = workerInfoDao.getWorkerPOByEmail(email);

        List<WorkerTastePO> tastePOS = tasteDao.getAll();
        // Partition
        List<WorkerTastePO> others = new ArrayList<>();
        WorkerTastePO self = null;
        for (WorkerTastePO tastePO : tastePOS) {
            if (tastePO.getEmail().equals(email))
                self = tastePO;
            else
                others.add(tastePO);
        }

        assert self != null;
        FeatureVector selfFeatureVector = new FeatureVector(email, self.getVector());
        // Add ability factor

        Comparator<FeatureVector> comparator = Comparator.comparing(vector ->
                VectorHelper.similarity(vector.getVector(), selfFeatureVector.getVector()));

        // Use cosine similarity to sort todo consider whether to add worker view log vector
        List<FeatureVector> otherVectors = others.stream()
                .map(e -> new FeatureVector(e.getEmail(), e.getVector()))
                .sorted(comparator)
                .collect(Collectors.toList());

        // Get top 10
        List<FeatureVector> top = otherVectors.subList(0, 10);
        List<WorkerPO> topWorkers = top.stream()
                .map(vector -> workerInfoDao.getWorkerPOByEmail(vector.getIdentity()))
                .limit(RECOMMENDATION_NUM)
                .collect(Collectors.toList());

        // Get recommendation just according to interests  todo add task's priority factor
        List<Integer> participatedTasks = workerInfoDao.getParticipatedTasks(email);
        List<Integer> target = topWorkers.stream().map(w -> workerInfoDao.getParticipatedTasks(w.getEmail()))
                .flatMap(li -> {
                    li.removeAll(participatedTasks);
                    return li.stream();
                }).distinct().collect(Collectors.toList());

        return target.stream().map(e -> Task.fromPO(taskDao.getTaskById(e))).collect(Collectors.toList());
    }
}
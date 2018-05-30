package top.minecode.service.ml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.minecode.dao.auto.TaskVectorDao;
import top.minecode.dao.auto.WorkerVectorDao;
import top.minecode.dao.worker.WorkerInfoDao;
import top.minecode.dao.workertask.TaskParticipationDao;
import top.minecode.domain.utils.Pair;
import top.minecode.domain.utils.VectorHelper;
import top.minecode.po.auto.TaskVectorPO;
import top.minecode.po.auto.WorkerVectorPO;
import top.minecode.po.worker.FinishedTaskParticipationPO;
import top.minecode.po.worker.WorkerPO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created on 2018/5/28.
 * Description:
 *
 * @author iznauy
 */
@Service
public class WorkerVectorCalculateService {

    private WorkerVectorDao workerVectorDao;

    private TaskVectorDao taskVectorDao;

    private WorkerInfoDao workerInfoDao;

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

    public WorkerInfoDao getWorkerInfoDao() {
        return workerInfoDao;
    }

    @Autowired
    public void setWorkerInfoDao(WorkerInfoDao workerInfoDao) {
        this.workerInfoDao = workerInfoDao;
    }

    // 定时任务
    // 由于有一定的计算量，安排在每天一点钟进行
    public void calculateWorkerVectors() {
        List<WorkerVectorPO> rawWorkerVectors = workerVectorDao.getAll();

        List<TaskVectorPO> taskVectors = taskVectorDao.getAll();
        Map<Integer, TaskVectorPO> id2TaskVector = taskVectors.stream()
                .collect(Collectors.toMap(TaskVectorPO::getTaskId, e -> e));

        List<WorkerVectorPO> resultVectors = new ArrayList<>();
        for (WorkerVectorPO rawVector: rawWorkerVectors) {
            List<FinishedTaskParticipationPO> finishedTaskParticipation =
                    workerInfoDao.getFinishedTasks(rawVector.getEmail());
            List<Pair<TaskVectorPO, Double>> values = finishedTaskParticipation
                    .stream().map(e -> new Pair<>(id2TaskVector.get(e.getTaskId()),
                            e.getScoreChange() / 100.0))
                    .collect(Collectors.toList());

            resultVectors.add(calculateSingleWorkerVector(rawVector, values));
        }

        workerVectorDao.batchUpdate(resultVectors);
    }

    private double calcLoss(List<Double> parameters, List<List<Double>> data, List<Double> targets, double norm) {
        // \frac{1}{2} \lambda \theta^T \theta + \frac{1}{2} \sum_{i=1}^{n} (\theta^T x - y)^2
        int size = data.size();
        double sums = 0.0;
        for (int i = 0; i < size; i++) {
            List<Double> x = data.get(i);
            double y = targets.get(i);
            double dot = VectorHelper.dot(parameters, x);
            sums += (dot - y) * (dot - y);
        }
        sums /= (2 * size);
        sums += 0.5 * norm * VectorHelper.dot(parameters, parameters);
        return sums;
    }

    private List<Double> calcGradient(List<Double> parameters,
                                      List<List<Double>> data, List<Double> targets, double norm) {
        List<Double> gradient = new ArrayList<>();
        int size = data.size();
        for (double param: parameters)
            gradient.add(size * norm * param);

        for (int i = 0; i < size; i++) {
            List<Double> x = data.get(i);
            double y = targets.get(i);
            double dot = VectorHelper.dot(parameters, x) - y;
            for (int j = 0; j < gradient.size(); j++)
                gradient.set(j, gradient.get(j) + dot * x.get(j));
        }

        for (int i = 0; i < gradient.size(); i++)
            gradient.set(i, gradient.get(i) / size);
        return gradient;
    }


    private WorkerVectorPO calculateSingleWorkerVector(WorkerVectorPO workerVector,
                                                       List<Pair<TaskVectorPO, Double>> rawData) {
        double learningRate = 0.01; // hyper-parameters
        double errorBound = 1.1;
        double norm = 1.0;
        int maxIterTimes = 100;

        List<Double> parameters = workerVector.getVector();
        List<Double> targets = rawData.stream().map(e -> e.r).collect(Collectors.toList());
        List<List<Double>> data = rawData.stream().map(e -> e.l.getVector()).collect(Collectors.toList());

        double preLoss = calcLoss(parameters, data, targets, norm);
        double loss;
        int iterTimes = 0;
        do {
            iterTimes += 1;
            List<Double> gradient = calcGradient(parameters, data, targets, norm);
            for (int i = 0; i < parameters.size(); i++)
                parameters.set(i, parameters.get(i) - learningRate * gradient.get(i));
            loss = calcLoss(parameters, data, targets, norm);
        } while (Math.abs(preLoss - loss) < errorBound && iterTimes < maxIterTimes);
        
        return new WorkerVectorPO(workerVector.getEmail(), parameters);
    }

}

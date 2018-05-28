package top.minecode.service.ml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.minecode.dao.auto.TaskVectorDao;
import top.minecode.dao.auto.WorkerVectorDao;
import top.minecode.dao.worker.WorkerInfoDao;
import top.minecode.po.auto.WorkerVectorPO;
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

    public void calculateWorkerVectors() {
        List<WorkerVectorPO> rawWorkerVectors = workerVectorDao.getAll();

        List<WorkerPO> workers = workerInfoDao.getAll();
        Map<String, WorkerPO> email2Worker = workers.stream().collect(Collectors.toMap(WorkerPO::getEmail, e -> e));

        List<WorkerVectorPO> resultVectors = new ArrayList<>();
        for (WorkerVectorPO rawVector: rawWorkerVectors) {

        }
    }

    private WorkerVectorPO calculateSingleWorkerVector(String email) {

        return null;
    }

}

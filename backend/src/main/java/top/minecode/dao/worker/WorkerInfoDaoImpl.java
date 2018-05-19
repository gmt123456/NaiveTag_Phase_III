package top.minecode.dao.worker;

import org.springframework.stereotype.Service;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.domain.user.worker.Worker;
import top.minecode.po.worker.WorkerPO;

import java.util.ArrayList;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
@Service("workInfoDao")
public class WorkerInfoDaoImpl implements WorkerInfoDao {

    private CommonOperation<WorkerPO> workerPOHelper = new CommonOperation<>(WorkerPO.class.getName());

    @Override
    public WorkerPO getWorkerPOByEmail(String email) {
        return workerPOHelper.getBySingleField("email", email);
    }

    @Override
    public boolean addWorker(WorkerPO worker) {
        return workerPOHelper.add(worker);
    }

    public static void main(String[] args) {
        WorkerPO workerPO = new WorkerPO();
        workerPO.setEmail("zy05160516@126.com");
        workerPO.setDollars(0.0);
        workerPO.setFinishedTaskParticipation(new ArrayList<>());
        workerPO.setOnGoingTaskParticipation(new ArrayList<>());
        workerPO.setAvatar("iznuay");
        workerPO.setName("ziyuan");
        WorkerInfoDao infoDao = new WorkerInfoDaoImpl();
        infoDao.addWorker(workerPO);
    }

}

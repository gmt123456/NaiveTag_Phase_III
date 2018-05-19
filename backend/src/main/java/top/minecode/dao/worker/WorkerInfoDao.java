package top.minecode.dao.worker;

import org.springframework.stereotype.Service;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.po.worker.WorkerPO;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
@Service
public class WorkerInfoDao {

    private CommonOperation<WorkerPO> workerPOHelper = new CommonOperation<>(WorkerPO.class.getName());


    public WorkerPO getWorkerPOByEmail(String email) {
        return workerPOHelper.getBySingleField("email", email);
    }


    public boolean addWorker(WorkerPO worker) {
        return workerPOHelper.add(worker);
    }

//    public static void main(String[] args) {
//        WorkerPO workerPO = new WorkerPO();
//        workerPO.setEmail("zy05160516@126.com");
//        workerPO.setJoinTime(Calendar.getInstance().getTime());
//        workerPO.setPassword("iznauy.top");
//        workerPO.setDollars(0.0);
//        workerPO.setOnGoingTaskParticipation(new ArrayList<>());
//        workerPO.setAvatar("iznuay");
//        workerPO.setName("ziyuan");
//        WorkerInfoDao infoDao = new WorkerInfoDaoImpl();
//        infoDao.addWorker(workerPO);
//    }

    public boolean updateWorkPO(WorkerPO workerPO) {
        return workerPOHelper.update(workerPO);
    }

}

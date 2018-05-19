package top.minecode.dao.worker;

import org.springframework.stereotype.Service;
import top.minecode.po.worker.WorkerPO;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
@Service
public interface WorkerInfoDao {

    WorkerPO getWorkerPOByEmail(String email);

    boolean addWorker(WorkerPO worker);

}

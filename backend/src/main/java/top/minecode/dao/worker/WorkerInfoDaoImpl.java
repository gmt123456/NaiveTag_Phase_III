package top.minecode.dao.worker;

import org.springframework.stereotype.Service;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.domain.user.worker.Worker;
import top.minecode.po.worker.WorkerPO;

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
}

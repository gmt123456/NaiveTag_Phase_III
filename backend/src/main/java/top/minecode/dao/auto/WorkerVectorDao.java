package top.minecode.dao.auto;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.dao.utils.HibernateUtils;
import top.minecode.po.auto.WorkerVectorPO;

import java.util.List;

/**
 * Created on 2018/5/28.
 * Description:
 *
 * @author iznauy
 */
@Repository
public class WorkerVectorDao {

    private CommonOperation<WorkerVectorPO> workerVectorHelper =
            new CommonOperation<>(WorkerVectorPO.class);

    public void add(WorkerVectorPO workerVectorPO) {
        workerVectorHelper.add(workerVectorPO);
    }

    public List<WorkerVectorPO> getAll() {
        return workerVectorHelper.getAll();
    }

    // 批量更新用户向量
    public void batchUpdate(List<WorkerVectorPO> workerVectorPOS) {
        Session session = HibernateUtils.getCurrentSession();
        for (WorkerVectorPO po: workerVectorPOS)
            session.update(po);
        session.close();
    }

    public WorkerVectorPO getVectorByEmail(String email) {
        return workerVectorHelper.getBySingleField("email", email);
    }



}

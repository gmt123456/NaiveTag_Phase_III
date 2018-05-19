package top.minecode.dao.worker;

import org.springframework.stereotype.Service;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.dao.utils.HibernateUtils;
import top.minecode.po.worker.FinishedTaskParticipationPO;
import top.minecode.po.worker.OnGoingTaskParticipationPO;
import top.minecode.po.worker.WorkerPO;

import java.util.List;

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

    public boolean updateWorkPO(WorkerPO workerPO) {
        return workerPOHelper.update(workerPO);
    }

    public List<FinishedTaskParticipationPO> getFinishedTasks(String email) {
        WorkerPO worker = getWorkerPOByEmail(email);
        List<Integer> finishedParticipation = worker.getFinishedTaskParticipation();
        String hql = "from " + OnGoingTaskParticipationPO.class.getName() + " t where t.id in (: ids)";
        List<FinishedTaskParticipationPO> results = HibernateUtils.getCurrentSession().createQuery(hql)
                .setParameterList("ids", finishedParticipation).list();
        HibernateUtils.closeSession();
        return results;
    }

}

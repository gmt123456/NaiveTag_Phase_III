package top.minecode.dao.workertask;

import org.springframework.stereotype.Repository;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.po.worker.OnGoingTaskParticipationPO;

/**
 * Created on 2018/5/20.
 * Description:
 *
 * @author iznauy
 */
@Repository
public class TaskParticipationDao {

    private CommonOperation<OnGoingTaskParticipationPO> onGoingTaskParticipationHelper =
            new CommonOperation<OnGoingTaskParticipationPO>(OnGoingTaskParticipationPO.class.getName());

    public void addOnGoingTaskParticipation(OnGoingTaskParticipationPO po) {
        onGoingTaskParticipationHelper.add(po);
    }

}

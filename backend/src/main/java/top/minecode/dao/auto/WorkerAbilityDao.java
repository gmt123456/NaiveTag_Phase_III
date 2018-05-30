package top.minecode.dao.auto;

import org.springframework.stereotype.Repository;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.po.auto.WorkerAbilityPO;

/**
 * Created on 2018/5/26.
 * Description:
 *
 * @author iznauy
 */
@Repository
public class WorkerAbilityDao {

    private CommonOperation<WorkerAbilityPO> commonOperation
            = new CommonOperation<>(WorkerAbilityPO.class);

    public void addWorkerAbility(WorkerAbilityPO abilityPO) {
        commonOperation.add(abilityPO);
    }

    public void updateWorkerAbility(WorkerAbilityPO abilityPO) {
        commonOperation.update(abilityPO);
    }


}

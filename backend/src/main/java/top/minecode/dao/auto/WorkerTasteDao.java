package top.minecode.dao.auto;

import org.springframework.stereotype.Repository;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.po.auto.WorkerTastePO;

import java.util.List;

/**
 * Created on 2018/5/26.
 * Description:
 *
 * @author iznauy
 */
@Repository
public class WorkerTasteDao {

    private CommonOperation<WorkerTastePO> commonOperation
             = new CommonOperation<WorkerTastePO>(WorkerTastePO.class);

    public void addTastePO(WorkerTastePO workerTastePO) {
        commonOperation.add(workerTastePO);
    }

    public void updateTastePO(WorkerTastePO workerTastePO) {
        commonOperation.update(workerTastePO);
    }

    public List<WorkerTastePO> getAll() {
        return commonOperation.getAll();
    }

}

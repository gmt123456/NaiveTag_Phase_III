package top.minecode.dao.workertask;

import org.springframework.stereotype.Repository;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.po.task.SpecificTaskPO;

/**
 * Created on 2018/5/21.
 * Description:
 *
 * @author iznauy
 */
@Repository
public class SpecificTaskDao {

    private CommonOperation<SpecificTaskPO> specificTaskHelper =
            new CommonOperation<SpecificTaskPO>(SpecificTaskPO.class.getName());

    public SpecificTaskPO getSpecificTaskById(int id) {
        return specificTaskHelper.getBySingleField("id", id);
    }

}

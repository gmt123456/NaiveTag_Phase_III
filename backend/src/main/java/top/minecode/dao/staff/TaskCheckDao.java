package top.minecode.dao.staff;

import org.springframework.stereotype.Repository;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.po.task.SubCheckTaskPO;

/**
 * Created on 2018/6/2.
 * Description:
 *
 * @author iznauy
 */
@Repository
public class TaskCheckDao {

    private CommonOperation<SubCheckTaskPO> subCheckTaskHelper =
            new CommonOperation<>(SubCheckTaskPO.class);

    public void addSubCheck(SubCheckTaskPO subCheckTaskPO) {
        subCheckTaskHelper.add(subCheckTaskPO);
    }

    public void updateSubCheck(SubCheckTaskPO po) {
        subCheckTaskHelper.update(po);
    }

}

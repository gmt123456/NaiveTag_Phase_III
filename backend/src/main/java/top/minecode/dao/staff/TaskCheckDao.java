package top.minecode.dao.staff;

import org.springframework.stereotype.Repository;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.po.task.CheckTaskPO;
import top.minecode.po.task.SubCheckTaskPO;

import java.util.List;

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

    private CommonOperation<CheckTaskPO> checkTaskHelper =
            new CommonOperation<>(CheckTaskPO.class);

    public void addSubCheck(SubCheckTaskPO subCheckTaskPO) {
        subCheckTaskHelper.add(subCheckTaskPO);
    }

    public void updateSubCheck(SubCheckTaskPO po) {
        subCheckTaskHelper.update(po);
    }

    public void addCheck(CheckTaskPO checkTaskPO) {
        checkTaskHelper.add(checkTaskPO);
    }

    public void updateCheck(CheckTaskPO checkTaskPO) {
        checkTaskHelper.update(checkTaskPO);
    }

    public List<SubCheckTaskPO> getSubChecks(int taskId) {
        return subCheckTaskHelper.getListBySingleField("taskId", taskId);
    }

    public CheckTaskPO getCheckById(int id) {
        return checkTaskHelper.getBySingleField("id", id);
    }

    public SubCheckTaskPO getSubCheckById(int id) {
        return subCheckTaskHelper.getBySingleField("subPartId", id);
    }

}

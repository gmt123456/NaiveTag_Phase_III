package top.minecode.dao.workertask;

import org.springframework.stereotype.Repository;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.po.task.TaskPO;

import java.util.List;

/**
 * Created on 2018/5/20.
 * Description:
 *
 * @author iznauy
 */
@Repository
public class TaskDao {

    private CommonOperation<TaskPO> taskHelper = new CommonOperation<>(TaskPO.class.getName());

    public TaskPO getTaskById(int id) {
        return taskHelper.getBySingleField("id", id);
    }

    public List<TaskPO> getTasksByIds(List<Integer> ids) {
        return taskHelper.getValuesInSpecificSet(ids, "id");
    }

    public boolean persist(TaskPO taskPO) {
        return taskHelper.update(taskPO);
    }
}

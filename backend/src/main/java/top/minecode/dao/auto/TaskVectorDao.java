package top.minecode.dao.auto;

import org.springframework.stereotype.Repository;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.po.auto.TaskVectorPO;

import java.util.List;

/**
 * Created on 2018/5/28.
 * Description:
 *
 * @author iznauy
 */
@Repository
public class TaskVectorDao {

    private CommonOperation<TaskVectorPO> taskVectorHelper =
            new CommonOperation<>(TaskVectorPO.class);

    public List<TaskVectorPO> getAll() {
        return taskVectorHelper.getAll();
    }

    public void addTaskVector(TaskVectorPO vectorPO) {
        taskVectorHelper.add(vectorPO);
    }

}

package top.minecode.dao.requester.task;

import org.springframework.stereotype.Repository;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.domain.task.TaskState;
import top.minecode.domain.task.requester.TaskItem;
import top.minecode.po.task.TaskPO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 2018/5/24.
 * Description:
 * @author Liao
 */

@Repository("requesterTaskDaoImple")
public class RequesterTaskDaoImpl implements RequesterTaskDao {

    private CommonOperation<TaskPO> taskOperation = new CommonOperation<>(TaskPO.class);

    @Override
    public List<TaskItem> getTaskItems(String email, TaskState state) {
        String hql = "from TaskPO t where t.email=" + email + " and t.taskState=" + state.toString();
        List<TaskPO> taskPOS = taskOperation.executeSQL(hql);
        return taskPOS.stream().map(TaskItem::new).collect(Collectors.toList());
    }

}

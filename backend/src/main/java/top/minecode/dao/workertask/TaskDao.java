package top.minecode.dao.workertask;

import org.springframework.stereotype.Repository;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.domain.task.TaskState;
import top.minecode.po.task.TaskPO;

import java.util.Calendar;
import java.sql.Date;
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

    public List<TaskPO> getAll() {
        return taskHelper.getAll();
    }

    public void update(TaskPO taskPO) {
        taskHelper.update(taskPO);
    }

    public List<TaskPO> getCriticalTasks() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date());
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        Date date = new Date(calendar.getTime().getTime());

        String hql = "select t from " + TaskPO.class.getName() + " t where t.taskState = '" + TaskState.ON_GOING + "' and t.endDate < '"
                + date.toString() + "'";
        System.out.println(hql);
        return taskHelper.executeSQL(hql);
    }

    public static void main(String[] args) {
        TaskDao dao = new TaskDao();
        List<TaskPO> tasks = dao.getCriticalTasks();
        for (TaskPO po: tasks) {
            System.out.println(po);
        }
    }

}

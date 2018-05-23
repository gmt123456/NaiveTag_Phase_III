package top.minecode.dao.workertask;

import org.springframework.stereotype.Repository;
import top.minecode.dao.utils.HibernateUtils;
import top.minecode.po.task.TaskPO;

import java.util.List;

/**
 * Created on 2018/5/23.
 * Description:
 *
 * @author iznauy
 */
@Repository
public class TaskSearchDao {

    public List<TaskPO> searchTasks(String key) {
        String hql = "select t from " + TaskPO.class.getName() + " t where " +
                "t.taskName like '%" + key + "%' or t.taskDescription like '%" + key + "%'" +
                " or t.readme like '%" + key + "%'";
        return HibernateUtils.getCurrentSession().createQuery(hql).list();
    }

}

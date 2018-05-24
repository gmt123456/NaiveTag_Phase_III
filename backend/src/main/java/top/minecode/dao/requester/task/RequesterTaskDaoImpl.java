package top.minecode.dao.requester.task;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.dao.utils.HibernateUtils;
import top.minecode.domain.task.TaskState;
import top.minecode.domain.task.requester.TaskItem;
import top.minecode.domain.task.requester.TaskParticipant;
import top.minecode.po.task.TaskPO;
import top.minecode.po.worker.WorkerPO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created on 2018/5/24.
 * Description:
 * @author Liao
 */

@Repository("requesterTaskDaoImpl")
public class RequesterTaskDaoImpl implements RequesterTaskDao {

    private CommonOperation<TaskPO> taskOperation = new CommonOperation<>(TaskPO.class);


    @Override
    public List<TaskParticipant> getTaskParticipants(String taskId) {
//        TaskPO taskPO = hibernateTemplate.get(TaskPO.class, taskId);
//        List<String> participants = taskPO.getParticipators();

        // Get participant's name, avatar and division score
        String hql1 = "select p from WorkerPO p where p.email in ?";
        // noinspection unchecked
//        List<WorkerPO> workers = taskOperation.executeSQL(hql1, participants);

        // Get participant's name and his completed pictures number
        String hql = "select new map (p.email , sum(p.picAmount)) from SubTaskParticipationPO p where p.taskId=" + taskId +
                " group by p.email";

        //noinspection unchecked
//        List<Map> participantsPics = (List<Map>) hibernateTemplate.find(hql);

        // Combine them into TaskParticipant
//        workers.stream().map(e -> new TaskParticipant(e.getName(), e.getAvatar(), e.getScore(), ))

        return null;
    }

    @Override
    public List<TaskItem> getTaskItems(String email, TaskState state) {
        String hql = "from TaskPO t where t.email=" + email + " and t.taskState=" + state.toString();
        List<TaskPO> taskPOS = taskOperation.executeSQL(hql);
        return taskPOS.stream().map(TaskItem::new).collect(Collectors.toList());
    }

    private int getCompletePictureNum(List<Object[]> table, String email) {
        return 0;
    }
}

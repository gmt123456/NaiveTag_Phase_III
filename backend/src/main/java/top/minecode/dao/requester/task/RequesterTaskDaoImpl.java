package top.minecode.dao.requester.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.domain.task.SubTaskState;
import top.minecode.domain.task.TaskState;
import top.minecode.domain.task.TaskType;
import top.minecode.domain.task.requester.RequesterTaskItem;
import top.minecode.domain.task.requester.RequesterTaskDetails;
import top.minecode.domain.task.requester.TaskParticipant;
import top.minecode.po.task.TaskPO;
import top.minecode.po.worker.WorkerPO;

import java.util.ArrayList;
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

    private static Logger log = LoggerFactory.getLogger(RequesterTaskDaoImpl.class);

    private CommonOperation<TaskPO> taskOperation = new CommonOperation<>(TaskPO.class);

    @Override
    public RequesterTaskDetails getTaskDetails(int taskId) {
        TaskPO task = taskOperation.getBySingleField("id", taskId);

        return new RequesterTaskDetails(task, getTaskProcess(taskId));
    }

    @Override
    public List<TaskParticipant> getTaskParticipants(int taskId) {
        // Get all participants and their completed pictures number
        String hql = "select new map (p.email as email , sum(p.picAmount) as picNum)" +
                " from SubTaskParticipationPO p where p.taskId=" +
                " group by p.email";
        List<Map> participantsPics = taskOperation.executeSQL(Map.class, hql, taskId);

        // Transfer it to a hashMap
        Map<String, Integer> participantsPicsMap = participantsPics.stream()
                .collect(Collectors.toMap(e -> (String) e.get("email"), e -> (Integer) e.get("picNum")));

        // Get WorkerPO by email in participantsPics
        String query = "from WorkerPO w where w.email in ?";
        List<WorkerPO> workers = taskOperation.executeSQL(WorkerPO.class, query, participantsPicsMap.keySet());

        // Combine them into TaskParticipant
        return workers.stream()
                .map(e -> new TaskParticipant(e.getName(), e.getAvatar(),
                        e.getScore(), participantsPicsMap.get(e.getEmail())))
                .collect(Collectors.toList());
    }

    @Override
    public List<RequesterTaskItem> getTaskItems(String email, TaskState state) {
        String hql = "from TaskPO t where t.email=" + email + " and t.taskState=" + state.toString();
        List<TaskPO> taskPOS = taskOperation.executeSQL(hql);
        return taskPOS.stream().map(RequesterTaskItem::new).collect(Collectors.toList());
    }

    private double getTaskProcess(int taskId) {
        String hql = "select t.subTaskState from SubTaskPO t where t.taskId=?";
        List<SubTaskState> states = taskOperation.executeSQL(SubTaskState.class, hql, taskId);

        if (states.size() == 0) {
            log.error("No sub tasks in task " + taskId);
            return 0.;
        }
        long finishedNum = states.stream().filter(e -> e == SubTaskState.FINISHED).count();

        return finishedNum * 1. / states.size();
    }

}

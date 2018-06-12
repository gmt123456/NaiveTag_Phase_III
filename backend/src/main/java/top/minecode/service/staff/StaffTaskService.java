package top.minecode.service.staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.minecode.dao.staff.StaffDao;
import top.minecode.dao.workertask.SpecificTaskDao;
import top.minecode.dao.workertask.SubTaskDao;
import top.minecode.dao.workertask.TaskDao;
import top.minecode.dao.workertask.TaskParticipationDao;
import top.minecode.domain.task.*;
import top.minecode.domain.user.worker.Division;
import top.minecode.po.admin.StaffPO;
import top.minecode.po.task.SpecificTaskPO;
import top.minecode.po.task.TaskPO;
import top.minecode.po.worker.OnGoingTaskParticipationPO;
import top.minecode.po.worker.SubTaskParticipationPO;
import top.minecode.service.workertask.WorkerTaskBasicService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created on 2018/6/1.
 * Description:
 *
 * @author iznauy
 */
@Service
public class StaffTaskService {

    private TaskDao taskDao;

    private StaffDao staffDao;

    private SpecificTaskDao specificTaskDao;

    private TaskParticipationDao participationDao;

    private SubTaskDao subTaskDao;

    public SubTaskDao getSubTaskDao() {
        return subTaskDao;
    }

    @Autowired
    public void setSubTaskDao(SubTaskDao subTaskDao) {
        this.subTaskDao = subTaskDao;
    }

    public SpecificTaskDao getSpecificTaskDao() {
        return specificTaskDao;
    }

    @Autowired
    public void setSpecificTaskDao(SpecificTaskDao specificTaskDao) {
        this.specificTaskDao = specificTaskDao;
    }

    public TaskParticipationDao getParticipationDao() {
        return participationDao;
    }

    @Autowired
    public void setParticipationDao(TaskParticipationDao participationDao) {
        this.participationDao = participationDao;
    }

    public TaskDao getTaskDao() {
        return taskDao;
    }

    @Autowired
    public void setTaskDao(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public StaffDao getStaffDao() {
        return staffDao;
    }

    @Autowired
    public void setStaffDao(StaffDao staffDao) {
        this.staffDao = staffDao;
    }

    public JoinTaskResponse joinTask(String email, int taskId) {

        TaskPO task = taskDao.getTaskById(taskId);

        if (task == null || task.getTaskState() != TaskState.ON_GOING)
            return new JoinTaskResponse(false, JoinTaskResponse.UNKNOWN_REASON);

        StaffPO staff = staffDao.getStaffByEmail(email);
        Set<Integer> participatedTasks = staff.getParticipatedTasks().keySet();

        if (participatedTasks.contains(taskId))
            return new JoinTaskResponse(false, JoinTaskResponse.HAS_ACCEPTED);

        OnGoingTaskParticipationPO participation = new OnGoingTaskParticipationPO();
        participation.setParticipatedSubTaskResultIds(new ArrayList<>());
        participation.setTaskId(taskId);
        participation.setUserEmail(email);
        participation.setStaffUser(true);

        participationDao.addOnGoingTaskParticipation(participation);

        staff.getParticipatedTasks().put(taskId, participation.getId());

        staffDao.updateStaff(staff);

        return new JoinTaskResponse(true, null);
    }

    public List<SubTask> getAllSubTasks(String email, int taskId, TaskType taskType) {
        TaskPO taskPO = taskDao.getTaskById(taskId);
        int specificTaskId = taskPO.getSpecificTasks().get(taskType);
        SpecificTaskPO specificTaskPO = specificTaskDao.getSpecificTaskById(specificTaskId);
        List<Integer> subTasksIds = specificTaskPO.getSubTasks(); // 获取到子任务的全部id

        return subTaskDao.getSubTasksByIdList(subTasksIds).stream()
                .filter(e -> e.getSubTaskState() == SubTaskState.COMMON).map(e -> SubTask.fromPO(e, taskId))
                .collect(Collectors.toList());
    }

    public List<SubTaskParticipation> getWorkerParticipation(String email, int taskId, SubTaskParticipationState subTaskParticipationState) {
        if (subTaskParticipationState == SubTaskParticipationState.EXPIRED ||
                subTaskParticipationState == SubTaskParticipationState.FINISHED)
            return new ArrayList<>();
        TaskPO taskPO = taskDao.getTaskById(taskId);
        if (taskPO.getTaskState() != TaskState.ON_GOING)
            return new ArrayList<>();
        List<OnGoingTaskParticipationPO> onGoingTaskParticipationPOs = participationDao.getOnGoingParticipation(email);
        OnGoingTaskParticipationPO participationPO = null;
        for (OnGoingTaskParticipationPO po: onGoingTaskParticipationPOs) {
            if (po.getTaskId() == taskId)
                participationPO = po;
        }
        if (participationPO == null)
            return null;

        List<Integer> subTaskParticipation = participationPO.getParticipatedSubTaskResultIds();

        return subTaskDao.getSubTaskParticipationByIds(subTaskParticipation).stream()
                .filter(e -> e.getState() == subTaskParticipationState).map(SubTaskParticipation::fromPO)
                .collect(Collectors.toList());

    }

    public List<Task> getParticipatedTasks(String staffEmail) {
        StaffPO staffPO = staffDao.getStaffByEmail(staffEmail);
        List<Integer> taskIds = new ArrayList<>(staffPO.getParticipatedTasks().keySet());
        List<TaskPO> rawTasks = taskDao.getTasksByIds(taskIds);
        return rawTasks.stream().filter(e -> e.getTaskState() == TaskState.ON_GOING).map(Task::fromPO).collect(Collectors.toList());
    }

    public List<Task> getTasks(String staffEmail) {
        List<TaskPO> taskPOS = taskDao.getCriticalTasks();
        StaffPO staffPO = staffDao.getStaffByEmail(staffEmail);
        Set<Integer> participatedTasks = staffPO.getParticipatedTasks().keySet();
        System.out.println(participatedTasks);
        return taskPOS.stream().filter(e -> !participatedTasks.contains(e.getId())).map(Task::fromPO)
                .collect(Collectors.toList());
    }

    public TaskSpecification getTaskDetail(String staffEmail, int taskId) {
        TaskPO taskPO = taskDao.getTaskById(taskId);
        StaffPO staffPO = staffDao.getStaffByEmail(staffEmail);
        boolean accepted = false;
        if (staffPO.getParticipatedTasks().keySet().contains(taskId))
            accepted = true;

        TaskState state = taskPO.getTaskState();
        Division requiredDivision = taskPO.getLowestDivision();

        String taskBackground = taskPO.getReadme();

        return new TaskSpecification(Task.fromPO(taskPO), state, accepted, requiredDivision,
                taskBackground, true, 0.0, 0.0, taskPO.getBackgroundImage());

    }


}

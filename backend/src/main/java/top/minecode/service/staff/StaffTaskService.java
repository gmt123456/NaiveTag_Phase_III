package top.minecode.service.staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.minecode.dao.staff.StaffDao;
import top.minecode.dao.workertask.SubTaskDao;
import top.minecode.dao.workertask.TaskDao;
import top.minecode.dao.workertask.TaskParticipationDao;
import top.minecode.domain.task.*;
import top.minecode.po.admin.StaffPO;
import top.minecode.po.task.TaskPO;
import top.minecode.po.worker.OnGoingTaskParticipationPO;
import top.minecode.service.workertask.WorkerTaskBasicService;

import java.util.ArrayList;
import java.util.List;
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

    private TaskParticipationDao participationDao;

    private WorkerTaskBasicService workerTaskBasicService;

    private SubTaskDao subTaskDao;

    public WorkerTaskBasicService getWorkerTaskBasicService() {
        return workerTaskBasicService;
    }

    public SubTaskDao getSubTaskDao() {
        return subTaskDao;
    }

    @Autowired
    public void setSubTaskDao(SubTaskDao subTaskDao) {
        this.subTaskDao = subTaskDao;
    }

    @Autowired
    public void setWorkerTaskBasicService(WorkerTaskBasicService workerTaskBasicService) {
        this.workerTaskBasicService = workerTaskBasicService;
    }

    public TaskParticipationDao getParticipationDao() {
        return participationDao;
    }

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
        List<Integer> participatedTasks = staff.getParticipatedTasks();

        if (participatedTasks.contains(taskId))
            return new JoinTaskResponse(false, JoinTaskResponse.HAS_ACCEPTED);

        OnGoingTaskParticipationPO participation = new OnGoingTaskParticipationPO();
        participation.setParticipatedSubTaskResultIds(new ArrayList<>());
        participation.setTaskId(taskId);
        participation.setUserEmail(email);
        participation.setStaffUser(false);

        participationDao.addOnGoingTaskParticipation(participation);
        participatedTasks.add(taskId);
        staffDao.updateStaff(staff);

        return new JoinTaskResponse(true, null);
    }

    public List<SubTask> getAllSubTasks(String email, int taskId, TaskType taskType) {
        return workerTaskBasicService.getAllSubTasks(email, taskId, taskType);
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



}

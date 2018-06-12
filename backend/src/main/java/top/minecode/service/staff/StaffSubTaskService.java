package top.minecode.service.staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.minecode.dao.staff.StaffDao;
import top.minecode.dao.workertask.SubTaskDao;
import top.minecode.dao.workertask.TaskDao;
import top.minecode.dao.workertask.TaskParticipationDao;
import top.minecode.domain.task.*;
import top.minecode.po.admin.StaffPO;
import top.minecode.po.task.SubTaskPO;
import top.minecode.po.task.TaskPO;
import top.minecode.po.worker.OnGoingTaskParticipationPO;
import top.minecode.po.worker.SubTaskParticipationPO;
import top.minecode.service.workertask.WorkerSpecificTaskService;

import java.util.Calendar;
import java.util.Date;

/**
 * Created on 2018/6/6.
 * Description:
 *
 * @author iznauy
 */
@Service
public class StaffSubTaskService {

    private StaffDao staffDao;

    private TaskDao taskDao;

    private SubTaskDao subTaskDao;

    private TaskParticipationDao participationDao;

    private WorkerSpecificTaskService workerSpecificTaskService;

    public WorkerSpecificTaskService getWorkerSpecificTaskService() {
        return workerSpecificTaskService;
    }

    @Autowired
    public void setWorkerSpecificTaskService(WorkerSpecificTaskService workerSpecificTaskService) {
        this.workerSpecificTaskService = workerSpecificTaskService;
    }

    public SubTaskDao getSubTaskDao() {
        return subTaskDao;
    }

    @Autowired
    public void setSubTaskDao(SubTaskDao subTaskDao) {
        this.subTaskDao = subTaskDao;
    }

    public TaskDao getTaskDao() {
        return taskDao;
    }

    @Autowired
    public void setTaskDao(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public TaskParticipationDao getParticipationDao() {
        return participationDao;
    }

    @Autowired
    public void setParticipationDao(TaskParticipationDao participationDao) {
        this.participationDao = participationDao;
    }

    public StaffDao getStaffDao() {
        return staffDao;
    }

    @Autowired
    public void setStaffDao(StaffDao staffDao) {
        this.staffDao = staffDao;
    }

    public SubTaskAcceptResponse acceptSubTask(String staffEmail, int taskId, int subTaskId, TaskType taskType) {

        TaskPO task = taskDao.getTaskById(taskId);

        StaffPO staffPO = staffDao.getStaffByEmail(staffEmail);
        if (!staffPO.getParticipatedTasks().keySet().contains(taskId))
            return new SubTaskAcceptResponse(false, SubTaskAcceptResponse.UN_KNOWN);
        SubTaskPO subTaskPO = subTaskDao.getSubTaskById(subTaskId);
        if (subTaskPO == null || subTaskPO.getTaskType() != taskType || subTaskPO.getSubTaskState() != SubTaskState.COMMON)
            return new SubTaskAcceptResponse(false, SubTaskAcceptResponse.UN_KNOWN);
        System.out.println(staffPO.getParticipatedTaskEvaluations());
        System.out.println(staffPO.getParticipatedTaskEvaluations() == null);
        int taskParticipationId = staffPO.getParticipatedTasks().get(taskId);
        OnGoingTaskParticipationPO onParticipationPO = participationDao.getOnGoingTaskParticipation(taskParticipationId);

        SubTaskParticipationPO participationPO = new SubTaskParticipationPO();
        participationPO.setTaskId(taskId);
        participationPO.setSubTaskId(subTaskId);
        participationPO.setSubTaskType(taskType);
        participationPO.setCover(subTaskPO.getCover());
        participationPO.setPicAmount(subTaskPO.getPicList().size());
        participationPO.setState(SubTaskParticipationState.DOING);
        participationPO.setEmail(staffEmail);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, 3);

        participationPO.setExpiredDate(task.getEndDate().before(calendar.getTime()) ?
                task.getEndDate() : calendar.getTime());

        subTaskDao.addSubTaskParticipation(participationPO);

        Integer newId = participationPO.getId(); // 增加ID
        onParticipationPO.getParticipatedSubTaskResultIds().add(newId);
        participationDao.updateOnGoingTaskParticipation(onParticipationPO);

        subTaskPO.setCurrentDoingWorker(staffEmail);
        subTaskPO.setSubTaskState(SubTaskState.LOCKED);

        subTaskDao.updateSubTask(subTaskPO);

        return new SubTaskAcceptResponse(true,null);
    }

    public SubTaskDetail getSubTaskDetail(String email, int taskId, int subTaskId, TaskType taskType) {
        return workerSpecificTaskService.getSubTaskDetail(email, taskId, subTaskId, taskType);
    }

    public TaskCommitResponse commitTask(String staffEmail, int taskId, int subTaskId, TaskType taskType) {

        SubTaskParticipationPO participationPO = participationDao.getWorkerSubTaskParticipation(staffEmail, taskId, subTaskId);
        if (participationPO.getTags().keySet().size() != participationPO.getPicAmount())
            return new TaskCommitResponse(false);

        participationPO.setCommitDate(new Date());
        participationPO.setState(SubTaskParticipationState.FINISHED);
        participationPO.setAccept(true);
        participationPO.setErrorRate(0.0);
        participationPO.setEvaluated(true);

        return new TaskCommitResponse(true);

    }

}

package top.minecode.service.staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.minecode.dao.staff.StaffDao;
import top.minecode.dao.staff.TaskCheckDao;
import top.minecode.dao.workertask.TaskDao;
import top.minecode.domain.task.*;
import top.minecode.domain.taskcheck.SubCheckTaskState;
import top.minecode.domain.taskcheck.SubTaskCheck;
import top.minecode.domain.taskcheck.TaskCheckSpecification;
import top.minecode.po.admin.StaffPO;
import top.minecode.po.task.CheckTaskPO;
import top.minecode.po.task.SubCheckTaskPO;
import top.minecode.po.task.TaskPO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created on 2018/6/2.
 * Description:
 *
 * @author iznauy
 */
@Service
public class StaffTaskCheckService {

    private TaskDao taskDao;

    private StaffDao staffDao;

    private TaskCheckDao checkDao;

    public TaskCheckDao getCheckDao() {
        return checkDao;
    }

    @Autowired
    public void setCheckDao(TaskCheckDao checkDao) {
        this.checkDao = checkDao;
    }

    public StaffDao getStaffDao() {
        return staffDao;
    }

    @Autowired
    public void setStaffDao(StaffDao staffDao) {
        this.staffDao = staffDao;
    }

    public TaskDao getTaskDao() {
        return taskDao;
    }

    @Autowired
    public void setTaskDao(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public List<Task> getAllUnfinishedTasks(String email) {
        StaffPO staff = staffDao.getStaffByEmail(email);
        Set<Integer> participatedEvaluationTaskIds = staff.getParticipatedTaskEvaluations().keySet();
        return taskDao.getAll().stream().filter(e -> e.getTaskState() == TaskState.ON_GOING)
                .filter(e -> !participatedEvaluationTaskIds.contains(e.getId())).map(Task::fromPO).collect(Collectors.toList());
    }

    public List<Task> getMyParticipation(String email) {
        StaffPO staff = staffDao.getStaffByEmail(email);
        Set<Integer> participatedEvaluationTaskIds = staff.getParticipatedTaskEvaluations().keySet();
        return taskDao.getAll().stream().filter(e -> e.getTaskState() == TaskState.ON_GOING)
                .filter(e -> participatedEvaluationTaskIds.contains(e.getId())).map(Task::fromPO).collect(Collectors.toList());
    }

    public TaskCheckSpecification getTaskSpecification(String email, int taskId) {
        TaskPO taskPO = taskDao.getTaskById(taskId);
        StaffPO staffPO = staffDao.getStaffByEmail(email);
        TaskCheckSpecification specification = new TaskCheckSpecification(Task.fromPO(taskPO), false,
                taskPO.getReadme(), taskPO.getBackgroundImage());
        if(!staffPO.getParticipatedTaskEvaluations().keySet().contains(taskId))
            specification.setParticipated(true);
        return specification;
    }

    public JoinTaskResponse joinTask(String email, int taskId) {

        StaffPO staffPO = staffDao.getStaffByEmail(email);
        if (staffPO.getParticipatedTaskEvaluations().keySet().contains(taskId))
            return new JoinTaskResponse(false, null);

        CheckTaskPO checkPO = new CheckTaskPO();
        checkPO.setTaskId(taskId);
        checkPO.setStaffEmail(email);
        checkPO.setDoingParticipationIds(new ArrayList<>());

        checkDao.addCheck(checkPO);
        staffPO.getParticipatedTaskEvaluations().put(taskId, checkPO.getId());
        staffDao.updateStaff(staffPO);

        return new JoinTaskResponse(true, null);
    }

    public List<SubTaskCheck> getUnCheckedSubTasks(int taskId, TaskType taskType) {
        return checkDao.getSubChecks(taskId).stream().filter(e -> e.getCheckTaskState() == SubCheckTaskState.unaccepted)
                .filter(e -> e.getSubTaskType() == taskType).map(SubTaskCheck::fromPO).collect(Collectors.toList());
    }

    public List<SubTaskCheck> getOnGoingSubTaskChecks(int taskId, String email, TaskType taskType) {
        return checkDao.getSubChecks(taskId).stream().filter(e -> e.getSubTaskType() == taskType)
                .filter(e -> e.getCheckTaskState() == SubCheckTaskState.doing)
                .filter(e -> e.getAcceptorEmail().equals(email))
                .map(SubTaskCheck::fromPO)
                .collect(Collectors.toList());

    }

    public SubCheckTaskAcceptResponse acceptSubCheckTask(String email, int subParticipationId, int taskId) {

        SubCheckTaskPO subCheckTaskPO = checkDao.getSubCheckById(subParticipationId);
        if (subCheckTaskPO == null || subCheckTaskPO.getCheckTaskState() != SubCheckTaskState.unaccepted)
            return new SubCheckTaskAcceptResponse(false, SubTaskAcceptResponse.UN_KNOWN);

        subCheckTaskPO.setAcceptorEmail(email);
        subCheckTaskPO.setCheckTaskState(SubCheckTaskState.doing);

        checkDao.updateSubCheck(subCheckTaskPO);

        StaffPO staffPO = staffDao.getStaffByEmail(email);
        int taskCheckParticipationId = staffPO.getParticipatedTaskEvaluations().get(taskId);
        CheckTaskPO checkTaskPO = checkDao.getCheckById(taskCheckParticipationId);
        checkTaskPO.getDoingParticipationIds().add(subParticipationId);

        checkDao.updateCheck(checkTaskPO);

        return new SubCheckTaskAcceptResponse(true, null);
    }

}

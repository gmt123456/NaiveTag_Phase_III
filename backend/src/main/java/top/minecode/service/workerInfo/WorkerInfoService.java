package top.minecode.service.workerInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import top.minecode.dao.log.WorkerLogDao;
import top.minecode.dao.worker.RankDao;
import top.minecode.dao.worker.WorkerInfoDao;
import top.minecode.dao.workertask.TaskDao;
import top.minecode.domain.DateAndValue;
import top.minecode.domain.task.FinishedTask;
import top.minecode.domain.task.Task;
import top.minecode.domain.user.worker.Rank;
import top.minecode.domain.user.worker.WorkerBasicInfo;
import top.minecode.domain.user.worker.WorkerRecentActivity;
import top.minecode.po.log.LoginLogPO;
import top.minecode.po.log.TaskCommitmentLogPO;
import top.minecode.po.log.WorkerAccountLogPO;
import top.minecode.po.log.WorkerScoreChangeLogPO;
import top.minecode.po.task.TaskPO;
import top.minecode.po.worker.FinishedTaskParticipationPO;
import top.minecode.po.worker.OnGoingTaskParticipationPO;
import top.minecode.po.worker.WorkerPO;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
@Service
public class WorkerInfoService {

    private WorkerInfoDao workerInfoDao;

    private RankDao rankDao;

    private WorkerLogDao workerLogDao;

    private TaskDao taskDao;

    public TaskDao getTaskDao() {
        return taskDao;
    }

    @Autowired
    public void setTaskDao(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public WorkerLogDao getWorkerLogDao() {
        return workerLogDao;
    }

    @Autowired
    public void setWorkerLogDao(WorkerLogDao workerLogDao) {
        this.workerLogDao = workerLogDao;
    }

    public WorkerInfoDao getWorkerInfoDao() {
        return workerInfoDao;
    }

    @Autowired
    public void setWorkerInfoDao(WorkerInfoDao workerInfoDao) {
        this.workerInfoDao = workerInfoDao;
    }

    public RankDao getRankDao() {
        return rankDao;
    }

    @Autowired
    public void setRankDao(RankDao rankDao) {
        this.rankDao = rankDao;
    }

    public String getAvatar(String email) {
        WorkerPO workerPO = workerInfoDao.getWorkerPOByEmail(email);
        return workerPO.getAvatar();
    }

    public List<Rank> getRankTable() {
        List<Rank> ranks = rankDao.getRanks().stream().map(Rank::convert).collect(Collectors.toList());
        Collections.sort(ranks);
        return ranks;
    }

    public WorkerBasicInfo getBasicInfo(String email) {
        WorkerPO workerPO = workerInfoDao.getWorkerPOByEmail(email);
        int rank = rankDao.getRankByEmail(email);
        List<LoginLogPO> logs = workerLogDao.getLoginLogByEmail(email);

        Date lastVisit = null;
        if (logs.size() >= 2)
            lastVisit = logs.get(logs.size() - 2).getLoginTime();

        return new WorkerBasicInfo(workerPO.getAvatar(), workerPO.getName(), workerPO.getEmail(),
                lastVisit, rank, workerPO.getJoinTime(), workerPO.getDollars(), workerPO.getScore());
    }

    private static boolean isSameDay(Date date1, Date date2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        int year1 = calendar.get(Calendar.YEAR);
        int day1 = calendar.get(Calendar.DAY_OF_YEAR);

        calendar.setTime(date2);
        int year2 = calendar.get(Calendar.YEAR);
        int day2 = calendar.get(Calendar.DAY_OF_YEAR);

        return year1 == year2 && day1 == day2;
    }

    private List<DateAndValue> getDollarChanges(String email) {
        List<WorkerAccountLogPO> accountLogs = workerLogDao.getAccountLogByEmail(email);
        List<DateAndValue> target = new ArrayList<>();
        for (WorkerAccountLogPO accountLog: accountLogs) {
            Date current = accountLog.getTime();
            boolean find = false;
            for (DateAndValue aTarget : target) {
                if (isSameDay(current, aTarget.getDate())) {
                    aTarget.addValue(accountLog.getDollars());
                    find = true;
                    break;
                }
            }
            if (!find)
                target.add(new DateAndValue(current, accountLog.getDollars()));
        }
        return target;
    }

    private List<DateAndValue> getScoreChanges(String email) {
        List<WorkerScoreChangeLogPO> scoreLogs = workerLogDao.getScoreChangeLogByEmail(email);
        List<DateAndValue> target = new ArrayList<>();
        for (WorkerScoreChangeLogPO changeLog: scoreLogs) {
            Date current = changeLog.getTime();
            boolean find = false;
            for (DateAndValue pair: target) {
                if (isSameDay(current, pair.getDate())) {
                    if (current.after(pair.getDate())) {
                        pair.setValue(pair.getValue());
                        pair.setDate(current);
                    }
                    find = true;
                    break;
                }
            }
            if (!find)
                target.add(new DateAndValue(current, changeLog.getCurrentScore()));
        }
        return target;
    }

    private static boolean isLeapYear(int year) {
        return year % 400 == 0 || (year % 100 != 0 && year % 4 == 0);
    }

    private static int getDaysInMonth(int year, int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 2:
                return isLeapYear(year) ? 29 : 28;
            default:
                return 30;
        }
    }

    private List<DateAndValue> getActivity(String email) {
        List<DateAndValue> results = new ArrayList<>();
        List<TaskCommitmentLogPO> commitLogs = workerLogDao.getTaskCommitmentByEmail(email);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        for (int i = 0; i < 12; i++) {
            calendar.set(Calendar.MONTH, i);
            int days = getDaysInMonth(calendar.get(Calendar.YEAR), i + 1);
            for (int j = 1; j <= days; j++) {
                calendar.set(Calendar.DAY_OF_MONTH, j);
                results.add(new DateAndValue(calendar.getTime(), 0));
            }
        }
        for (TaskCommitmentLogPO commitLog: commitLogs) {
            Date current = commitLog.getCommitTime();
            calendar.setTime(current);
            int pos = calendar.get(Calendar.DAY_OF_YEAR) - 1;
            results.get(pos).addValue(1);
        }
        return results;
    }


    public WorkerRecentActivity getRecentActivity(String email) {
        return new WorkerRecentActivity(getDollarChanges(email), getScoreChanges(email), getActivity(email));
    }


    public List<FinishedTask> getFinishedTasks(String email) {
        List<FinishedTaskParticipationPO> rawParticipation = workerInfoDao.getFinishedTasks(email);
        Map<Integer, FinishedTaskParticipationPO> idToParticipation = rawParticipation.stream()
                .collect(Collectors.toMap(FinishedTaskParticipationPO::getTaskId, e -> e));
        List<Integer> taskIds = new ArrayList<>(idToParticipation.keySet());
        List<TaskPO> rawTasks = taskDao.getTasksByIds(taskIds);
        List<FinishedTask> results = new ArrayList<>();
        for (TaskPO po: rawTasks) {
            FinishedTaskParticipationPO finishedTaskParticipation = idToParticipation.get(po.getId());
            results.add(FinishedTask.fromPO(po, finishedTaskParticipation));
        }
        return results;
    }

    public List<Task> getOngoingTasks(String email) {
        List<OnGoingTaskParticipationPO> onGoingTaskParticipation = workerInfoDao.getOnGoingTasks(email);
        List<Integer> taskIds = onGoingTaskParticipation.stream().map(OnGoingTaskParticipationPO::getTaskId)
                .collect(Collectors.toList());
        List<TaskPO> rawTasks = taskDao.getTasksByIds(taskIds);
        return rawTasks.stream().map(Task::fromPO).collect(Collectors.toList());
    }

}

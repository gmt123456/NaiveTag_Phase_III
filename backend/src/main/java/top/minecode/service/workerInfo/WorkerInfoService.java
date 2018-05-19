package top.minecode.service.workerInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import top.minecode.dao.log.WorkerLogDao;
import top.minecode.dao.worker.RankDao;
import top.minecode.dao.worker.WorkerInfoDao;
import top.minecode.domain.DateAndValue;
import top.minecode.domain.task.FinishedTask;
import top.minecode.domain.task.Task;
import top.minecode.domain.user.worker.Rank;
import top.minecode.domain.user.worker.Worker;
import top.minecode.domain.user.worker.WorkerBasicInfo;
import top.minecode.domain.user.worker.WorkerRecentActivity;
import top.minecode.domain.utils.Pair;
import top.minecode.po.log.LoginLogPO;
import top.minecode.po.log.TaskCommitmentLogPO;
import top.minecode.po.log.WorkerAccountLogPO;
import top.minecode.po.log.WorkerScoreChangeLogPO;
import top.minecode.po.worker.FinishedTaskParticipationPO;
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
    @Qualifier("workInfoDao")
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

    private List<DateAndValue> getActivity(String email) {
        List<TaskCommitmentLogPO> commitLogs = workerLogDao.getTaskCommitmentByEmail(email);
        List<DateAndValue> target = new ArrayList<>();
        for (TaskCommitmentLogPO commitLog: commitLogs) {
            Date current = commitLog.getCommitTime();
            boolean find = false;
            for (DateAndValue pair: target) {
                if (isSameDay(current, commitLog.getCommitTime())) {
                    find = true;
                    pair.addValue(1);
                    break;
                }
            }
            if (!find)
                target.add(new DateAndValue(current, 1));
        }
        return target;
    }

    public WorkerRecentActivity getRecentActivity(String email) {
        return new WorkerRecentActivity(getDollarChanges(email), getScoreChanges(email), getActivity(email));
    }


    public List<FinishedTask> getFinishedTasks(String email) {
        List<FinishedTaskParticipationPO> rawParticipation = workerInfoDao.getFinishedTasks(email);

    }

    public List<Task> getOngoingTasks(String email) {
        return null;
    }

}

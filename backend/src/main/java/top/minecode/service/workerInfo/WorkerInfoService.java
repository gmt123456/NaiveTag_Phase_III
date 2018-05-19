package top.minecode.service.workerInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import top.minecode.dao.log.WorkerLogDao;
import top.minecode.dao.worker.RankDao;
import top.minecode.dao.worker.WorkerInfoDao;
import top.minecode.domain.DateAndValue;
import top.minecode.domain.user.worker.Rank;
import top.minecode.domain.user.worker.Worker;
import top.minecode.domain.user.worker.WorkerBasicInfo;
import top.minecode.domain.user.worker.WorkerRecentActivity;
import top.minecode.po.log.LoginLogPO;
import top.minecode.po.worker.WorkerPO;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
@Service("workerInfoService")
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

    private List<DateAndValue> getDollarChanges(String email) {
        return null;
    }

    private List<DateAndValue> getScoreChanges(String email) {
        return null;
    }

    private List<DateAndValue> getActivity(String email) {
        return null;
    }

    public WorkerRecentActivity getRecentActivity(String email) {
        return new WorkerRecentActivity(getDollarChanges(email), getScoreChanges(email), getActivity(email));
    }
}

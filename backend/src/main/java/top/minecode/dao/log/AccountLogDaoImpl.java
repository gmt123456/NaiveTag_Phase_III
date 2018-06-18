package top.minecode.dao.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.domain.user.requester.AccountLog;
import top.minecode.po.log.RequesterAccountLogPO;
import top.minecode.po.log.RequesterAccountLogPO.ChangeType;
import top.minecode.po.log.WorkerAccountLogPO;
import top.minecode.po.log.WorkerAccountLogPO.WorkerAccountChangeType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 2018/5/23.
 * Description:
 *
 * @author Liao
 */
@Repository("accountLogDaoImpl")
public class AccountLogDaoImpl implements AccountLogDao {

    private static final Logger log = LoggerFactory.getLogger(AccountLogDaoImpl.class);

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private CommonOperation<RequesterAccountLogPO> requesterAccountOperation =
            new CommonOperation<>(RequesterAccountLogPO.class);
    private CommonOperation<WorkerAccountLogPO> workerAccountOperation =
            new CommonOperation<>(WorkerAccountLogPO.class);

    @Override
    public void log(String email, double dollars, double balance, ChangeType changeType) {
        RequesterAccountLogPO logPO = new RequesterAccountLogPO(email, dollars, balance, new Date(), changeType);
        requesterAccountOperation.add(logPO);
    }

    @Override
    public void log(String email, double dollars, double balance, ChangeType changeType, Date date) {
        RequesterAccountLogPO logPO = new RequesterAccountLogPO(email, dollars, balance, date, changeType);
        requesterAccountOperation.add(logPO);
    }

    @Override
    public List<AccountLog> getLogs(String email, int page, int pageSize) {
        String hql = "select new top.minecode.domain.user.requester.AccountLog(t) from " +
                " RequesterAccountLogPO t where t.userEmail=? order by t.time desc";
        //noinspection unchecked
        return (List<AccountLog>) CommonOperation.getPage(page, pageSize, hql, email);
    }

    @Override
    public List<WorkerAccountLogPO> getWorkerAccountLogs(WorkerAccountChangeType changeType) {
        return workerAccountOperation.getAll().stream()
                .filter(po -> po.getType() == changeType)
                .sorted(Comparator.comparing(WorkerAccountLogPO::getTime))
                .collect(Collectors.toList());
    }

    @Override
    public List<RequesterAccountLogPO> getRequesterAccountLogs(ChangeType changeType) {
        return requesterAccountOperation.getAll().stream()
                .filter(po -> po.getType() == changeType)
                .sorted(Comparator.comparing(RequesterAccountLogPO::getTime))
                .collect(Collectors.toList());
    }
}

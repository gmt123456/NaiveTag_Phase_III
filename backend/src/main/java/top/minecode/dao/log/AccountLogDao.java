package top.minecode.dao.log;

import top.minecode.domain.user.requester.AccountLog;
import top.minecode.po.log.RequesterAccountLogPO;
import top.minecode.po.log.RequesterAccountLogPO.ChangeType;
import top.minecode.po.log.WorkerAccountLogPO;
import top.minecode.po.log.WorkerAccountLogPO.WorkerAccountChangeType;

import java.util.Date;
import java.util.List;

/**
 * Created on 2018/5/23.
 * Description:
 * @author Liao
 */
public interface AccountLogDao {

    void log(String email, double dollars, double balance, ChangeType changeType);

    void log(String email, double dollars, double balance, ChangeType changeType, Date date);

    List<AccountLog> getLogs(String email, int page, int pageSize);

    List<WorkerAccountLogPO> getWorkerAccountLogs(WorkerAccountChangeType changeType);

    List<RequesterAccountLogPO> getRequesterAccountLogs(ChangeType changeType);
}

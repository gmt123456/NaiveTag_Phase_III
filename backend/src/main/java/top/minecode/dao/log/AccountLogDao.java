package top.minecode.dao.log;

import top.minecode.domain.user.requester.AccountLog;
import top.minecode.po.log.RequesterAccountLogPO;

import java.util.Date;
import java.util.List;

/**
 * Created on 2018/5/23.
 * Description:
 * @author Liao
 */
public interface AccountLogDao {

    void log(String email, double dollars, double balance, RequesterAccountLogPO.ChangeType changeType);

    void log(String email, double dollars, double balance, RequesterAccountLogPO.ChangeType changeType, Date date);

    List<AccountLog> getLogs(String email);

    List<AccountLog> getLogs(String email, int page, int pageSize);
}

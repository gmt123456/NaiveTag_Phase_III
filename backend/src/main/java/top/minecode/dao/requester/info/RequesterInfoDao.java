package top.minecode.dao.requester.info;

import top.minecode.domain.user.requester.AccountLog;
import top.minecode.domain.user.requester.Requester;
import top.minecode.domain.utils.ResultMessage;
import top.minecode.po.log.RequesterAccountLogPO;
import top.minecode.po.requester.RequesterPO;
import top.minecode.web.requester.info.ChangeCommand;

import java.util.List;

/**
 * Created on 2018/5/23.
 * Description:
 * @author Liao
 */
public interface RequesterInfoDao {

    Requester getRequester(String email);

    ResultMessage updateAccount(String email, double dollars, RequesterAccountLogPO.ChangeType changeType);

    List<AccountLog> getAccountLogs(String email, int page, int pageSize);

    ResultMessage updateRequesterInfo(ChangeCommand<RequesterPO> changeCommand, String email);
}

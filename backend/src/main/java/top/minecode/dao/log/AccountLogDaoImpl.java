package top.minecode.dao.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.domain.user.requester.AccountLog;
import top.minecode.po.log.RequesterAccountLogPO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

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

    private CommonOperation<RequesterAccountLogPO> accountOperation =
            new CommonOperation<>(RequesterAccountLogPO.class);

    private Function<RequesterAccountLogPO, AccountLog> function = e ->
            new AccountLog(e.getDollars(), e.getType().toString(),
                    e.getBalance(), dateFormat.format(e.getTime()));

    @Override
    public void log(String email, double dollars, double balance, RequesterAccountLogPO.ChangeType changeType) {
        RequesterAccountLogPO logPO = new RequesterAccountLogPO(email, dollars, balance, new Date(), changeType);
        accountOperation.add(logPO);
    }

    @Override
    public void log(String email, double dollars, double balance, RequesterAccountLogPO.ChangeType changeType, Date date) {
        RequesterAccountLogPO logPO = new RequesterAccountLogPO(email, dollars, balance, date, changeType);
        accountOperation.add(logPO);
    }

    @Override
    public List<AccountLog> getLogs(String email, int page, int pageSize) {
        String hql = "select new top.minecode.domain.user.requester.AccountLog(t) from " +
                " RequesterAccountLogPO t where t.userEmail=? order by t.time desc";
        //noinspection unchecked
        return (List<AccountLog>) CommonOperation.getPage(page, pageSize, hql, email);
    }
}

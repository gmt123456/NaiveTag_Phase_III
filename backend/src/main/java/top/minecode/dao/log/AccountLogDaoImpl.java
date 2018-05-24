package top.minecode.dao.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.domain.user.requester.AccountLog;
import top.minecode.po.log.RequesterAccountLogPO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created on 2018/5/23.
 * Description:
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
        // Calculate start and end of this page
        List<RequesterAccountLogPO> accountLogPOs = getAccountLogPOS(email);
        int pageLimit = accountLogPOs.size() / pageSize + 1;
        if (page > pageLimit || accountLogPOs.isEmpty()) {
            log.debug("Page number is larger than max page number");
            return Collections.emptyList();  // Unmodifiable list
        }

        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize - 1, accountLogPOs.size() - 1);

        if (start == end) {
            List<AccountLog> result = new ArrayList<>();
            result.add(function.apply(accountLogPOs.get(end)));
            return result;
        }

        return accountLogPOs.subList(start, end).stream().map(function).collect(Collectors.toList());
    }

    @Override
    public List<AccountLog> getLogs(String email) {
        List<RequesterAccountLogPO> accountLogPOs = getAccountLogPOS(email);

        return accountLogPOs.stream().map(function).collect(Collectors.toList());
    }

    private List<RequesterAccountLogPO> getAccountLogPOS(String email) {
        RequesterAccountLogPO logPO = new RequesterAccountLogPO();
        String hql = "from " + RequesterAccountLogPO.class.getSimpleName()
                + " t where t.email=" + email + " order by t.time desc";

        return accountOperation.executeSQL(hql);
    }
}

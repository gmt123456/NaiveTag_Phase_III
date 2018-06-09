package top.minecode.dao.requester.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.minecode.dao.log.AccountLogDao;
import top.minecode.dao.log.AuthenticationLogDao;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.domain.user.requester.AccountLog;
import top.minecode.domain.user.requester.Requester;
import top.minecode.domain.utils.TimeMessageConverter;
import top.minecode.domain.utils.ResultMessage;
import top.minecode.po.log.LoginLogPO;
import top.minecode.po.log.RequesterAccountLogPO;
import top.minecode.po.requester.RequesterPO;
import top.minecode.web.requester.info.ChangeCommand;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created on 2018/5/23.
 * Description:
 * @author Liao
 */
@Repository("requesterInfoDaoImpl")
public class RequesterInfoDaoImpl implements RequesterInfoDao {

    private static final Logger log = LoggerFactory.getLogger(RequesterInfoDaoImpl.class);

    private CommonOperation<RequesterPO> requesterOperation = new CommonOperation<>(RequesterPO.class);
    private AuthenticationLogDao authenticationLogDao;
    private AccountLogDao accountLogDao;

    @Autowired
    public void setAuthenticationLogDao(AuthenticationLogDao authenticationLogDao) {
        this.authenticationLogDao = authenticationLogDao;
    }

    @Autowired
    public void setAccountLogDao(AccountLogDao accountLogDao) {
        this.accountLogDao = accountLogDao;
    }

    @Override
    public Requester getRequester(String email) {
        RequesterPO requesterPO = getRequesterPO(email);
        if (requesterPO == null) {
            log.warn("Requester with email " + email + " not found");
            return null;  // TODO: 2018/5/23 or throw an exception?
        }

        // Get latest login record for this user
        LoginLogPO latestLoginLogPO = authenticationLogDao.getLatestLoginRecord(email);
        Date lastLoginTime = Optional.ofNullable(latestLoginLogPO).map(LoginLogPO::getLoginTime).orElse(null);

        String signMsg = TimeMessageConverter.convertBoth(requesterPO.getJoinTime(), lastLoginTime);
        double dollars = requesterPO.getDollars();

        return new Requester(requesterPO, signMsg, dollars);
    }

    @Override
    public ResultMessage updateRequesterInfo(ChangeCommand<RequesterPO> changeCommand, String email) {
        RequesterPO requesterPO = getRequesterPO(email);

        try {
            changeCommand.change(requesterPO);
            requesterOperation.update(requesterPO);
            return ResultMessage.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultMessage.failure(e.getMessage());
        }
    }

    @Override
    public ResultMessage updateAccount(String email, double dollars, RequesterAccountLogPO.ChangeType changeType) {
        RequesterPO po = getRequesterPO(email);
        // Add dollars in po to dollars passed in by BigDecimal
        BigDecimal oldData = new BigDecimal(po.getDollars());
        BigDecimal newData = new BigDecimal(dollars);
        BigDecimal result = oldData.add(newData);
        if (result.compareTo(BigDecimal.ZERO) < 0)
            return ResultMessage.failure("Money not enough");
        po.setDollars(result.doubleValue());

        requesterOperation.update(po);
        accountLogDao.log(email, dollars, result.doubleValue(), changeType);
        return ResultMessage.success();
    }

    @Override
    public List<AccountLog> getAccountLogs(String email, int page, int pageSize) {
        return accountLogDao.getLogs(email, page, pageSize);
    }

    private RequesterPO getRequesterPO(String email) {
        return requesterOperation.getBySingleField("email", email);
    }
}

package top.minecode.dao.requester.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.minecode.dao.log.AuthenticationLogDao;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.domain.user.requester.Requester;
import top.minecode.domain.utils.MoneyConverter;
import top.minecode.domain.utils.SignMessageConverter;
import top.minecode.po.log.LoginLogPO;
import top.minecode.po.requester.RequesterPO;

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

    @Autowired
    public void setAuthenticationLogDao(AuthenticationLogDao authenticationLogDao) {
        this.authenticationLogDao = authenticationLogDao;
    }

    @Override
    public Requester getRequester(String email) {
        RequesterPO requesterPO = requesterOperation.getBySingleField("email", email);
        if (requesterPO == null) {
            log.warn("Requester with email " + email + " not found");
            return null;  // TODO: 2018/5/23 or throw an exception?
        }

        // Get latest login record for this user
        LoginLogPO latestLoginLogPO = authenticationLogDao.getLatestLoginRecord(email);

        SignMessageConverter signMessageConverter = new SignMessageConverter();
        MoneyConverter moneyConverter = new MoneyConverter();

        String signMsg = signMessageConverter.convertBoth(requesterPO.getJoinTime(), latestLoginLogPO.getLoginTime());
        String dollars = moneyConverter.convert(requesterPO.getDollars());

        return new Requester(requesterPO, signMsg, dollars);
    }
}

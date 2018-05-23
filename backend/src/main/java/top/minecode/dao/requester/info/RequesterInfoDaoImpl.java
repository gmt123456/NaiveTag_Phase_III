package top.minecode.dao.requester.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import top.minecode.dao.log.AccountLogDao;
import top.minecode.dao.log.AuthenticationLogDao;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.domain.user.requester.AccountLog;
import top.minecode.domain.user.requester.Requester;
import top.minecode.domain.utils.MoneyConverter;
import top.minecode.domain.utils.SignMessageConverter;
import top.minecode.domain.utils.ResultMessage;
import top.minecode.po.log.LoginLogPO;
import top.minecode.po.log.RequesterAccountLogPO;
import top.minecode.po.requester.RequesterPO;
import top.minecode.service.util.Encryptor;
import top.minecode.web.requester.info.ChangeInfoCommand;

import java.math.BigDecimal;
import java.util.List;

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
    private Encryptor encryptor;

    @Autowired
    public void setEncryptor(Encryptor encryptor) {
        this.encryptor = encryptor;
    }

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

        SignMessageConverter signMessageConverter = new SignMessageConverter();
        MoneyConverter moneyConverter = new MoneyConverter();

        String signMsg = signMessageConverter.convertBoth(requesterPO.getJoinTime(), latestLoginLogPO.getLoginTime());
        String dollars = moneyConverter.convert(requesterPO.getDollars());

        return new Requester(requesterPO, signMsg, dollars);
    }

    @Override
    public ResultMessage updateRequesterInfo(ChangeInfoCommand changeInfo, String email) {
        RequesterPO requesterPO = getRequesterPO(email);

        // Find not null attributes and update them
        if (changeInfo.getAvatar() != null && changeInfo.getImagePosition() != null)
            updateAvatar(changeInfo.getAvatar(), changeInfo.getImagePosition(), requesterPO.getAvatar());

        // Update name
        if (changeInfo.getName() != null) {
            requesterPO.setName(changeInfo.getName());
        }

        // Update password
        if (changeInfo.getOldPassword() != null && changeInfo.getNewPassword() != null) {
            String oldPwd = changeInfo.getOldPassword();
            String newPwd = changeInfo.getNewPassword();

            if (!encryptor.encrypt(oldPwd, email).equals(requesterPO.getPassword())) {
                // Old password is not right
                log.info("Invalid old password for user " + email);
                return ResultMessage.failure("Invalid old password");
            } else {
                requesterPO.setPassword(encryptor.encrypt(newPwd, email));
            }
        }

        if (requesterOperation.update(requesterPO))
            return ResultMessage.success();

        log.error("Database error");
        return ResultMessage.failure("Update failed");
    }

    @Override
    public ResultMessage updateAccount(String email, double dollars) {
        RequesterPO po = getRequesterPO(email);
        // Add dollars in po to dollars passed in by BigDecimal
        BigDecimal oldData = new BigDecimal(po.getDollars());
        BigDecimal newData = new BigDecimal(dollars);
        BigDecimal result = oldData.add(newData);
        po.setDollars(result.doubleValue());

        accountLogDao.log(email, dollars, result.doubleValue(), RequesterAccountLogPO.ChangeType.RECHARGE);
        return ResultMessage.success();
    }

    @Override
    public List<AccountLog> getAccountLogs(String email, int page, int pageSize) {
        return accountLogDao.getLogs(email, page, pageSize);
    }

    /**
     * Update avatar's file by overwriting the old avatar, and the
     * url will not change
     */
    private void updateAvatar(MultipartFile avatar, List<Integer> imagePosition, String oldPath) {
        // TODO: 2018/5/23 update avatar here
    }

    private RequesterPO getRequesterPO(String email) {
        return requesterOperation.getBySingleField("email", email);
    }
}

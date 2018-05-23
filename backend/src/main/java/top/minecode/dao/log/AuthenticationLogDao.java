package top.minecode.dao.log;

import top.minecode.domain.user.UserType;
import top.minecode.po.log.LoginLogPO;

import java.util.Date;

/**
 * Created on 2018/5/19.
 * Description:
 * @author Liao
 */
public interface AuthenticationLogDao {

    /**
     * Add login record to database
     * @param userEmail user's email
     * @param loginTime login time
     * @param userType user's type (worker or requester)
     */
    void recordLogin(String userEmail, Date loginTime, UserType userType);

    /**
     * Add signup message to database
     * @param userEmail user's email
     * @param registerDate signup time
     * @param userType user's type (worker or requester)
     */
    void recordSignup(String userEmail, Date registerDate, UserType userType);

    /**
     * Get the time the user last login
     * @param userEmail user's email
     */
    LoginLogPO getLatestLoginRecord(String userEmail);
}

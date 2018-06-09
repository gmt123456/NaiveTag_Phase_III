package top.minecode.dao.log;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.domain.user.UserType;
import top.minecode.po.log.LoginLogPO;
import top.minecode.po.log.RegisterLogPO;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author Liao
 */
@Repository
public class AuthenticationLogDaoImpl implements AuthenticationLogDao {

    private CommonOperation<LoginLogPO> loginOperation = new CommonOperation<>(LoginLogPO.class);
    private CommonOperation<RegisterLogPO> registerOperation = new CommonOperation<>(RegisterLogPO.class);

    @Override
    public void recordLogin(String userEmail, Date loginTime, UserType userType) {
        loginOperation.add(new LoginLogPO(userEmail, loginTime, userType));
    }

    @Override
    public void recordSignup(String userEmail, Date registerDate, UserType userType) {
        registerOperation.add(new RegisterLogPO(userEmail, registerDate, userType));
    }

    @Override
    public LoginLogPO getLatestLoginRecord(String userEmail) {
        String hql = "from LoginLogPO log where log.userEmail=:mail order by log.loginTime desc";

        return CommonOperation.template(session -> {
            Query query = session.createQuery(hql);
            query.setParameter("mail", userEmail);
            List li = query.list();
            if (li.size() <= 1)
                return null;

            return (LoginLogPO) li.get(1);
        });
    }
}

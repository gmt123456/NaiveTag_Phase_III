package top.minecode.dao.log;

import org.springframework.stereotype.Repository;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.po.log.LoginLogPO;

import java.util.List;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
@Repository
public class WorkerLogDao {

    private CommonOperation<LoginLogPO> loginLogHelper = new CommonOperation<>(LoginLogPO.class.getName());

    public List<LoginLogPO> getLoginLogByEmail(String email) {
        return loginLogHelper.getListBySingleField("userEmail", email);
    }

}

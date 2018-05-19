package top.minecode.dao.log;

import org.springframework.stereotype.Repository;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.po.log.LoginLogPO;
import top.minecode.po.log.TaskCommitmentLogPO;
import top.minecode.po.log.WorkerAccountLogPO;
import top.minecode.po.log.WorkerScoreChangeLogPO;

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

    private CommonOperation<WorkerAccountLogPO> workerAccountLogHelper =
            new CommonOperation<>(WorkerAccountLogPO.class.getName());

    private CommonOperation<TaskCommitmentLogPO> taskCommitLogHelper =
            new CommonOperation<>(TaskCommitmentLogPO.class.getName());

    private CommonOperation<WorkerScoreChangeLogPO> workerScoreLogHelper =
            new CommonOperation<>(WorkerScoreChangeLogPO.class.getName());

    public List<LoginLogPO> getLoginLogByEmail(String email) {
        return loginLogHelper.getListBySingleField("userEmail", email);
    }

    public List<WorkerAccountLogPO> getAccountLogByEmail(String email) {
        return workerAccountLogHelper.getListBySingleField("userEmail", email);
    }

    public List<TaskCommitmentLogPO> getTaskCommitmentByEmail(String email) {
        return taskCommitLogHelper.getListBySingleField("userEmail", email);
    }

    public List<WorkerScoreChangeLogPO> getScoreChangeLogByEmail(String email) {
        return workerScoreLogHelper.getListBySingleField("userEmail", email);
    }

}

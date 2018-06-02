package top.minecode.dao.admin;

import org.springframework.stereotype.Repository;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.po.requester.RequesterPO;
import top.minecode.po.worker.WorkerPO;

import java.math.BigDecimal;
import java.util.function.Consumer;

/**
 * Created on 2018/6/2.
 * Description:
 * @author Liao
 */
@Repository("administrateUserDaoImpl")
public class AdministrateUserDaoImpl implements AdministrateUserDao {

    private CommonOperation<WorkerPO> workerOperation = new CommonOperation<>(WorkerPO.class);
    private CommonOperation<RequesterPO> requesterOperation = new CommonOperation<>(RequesterPO.class);

    @Override
    public boolean changePassword(String email, String newPassword) {

        // If it's a worker
        WorkerPO workerPO = workerOperation.getBySingleField("email", email);
        if (workerPO != null) {
            workerPO.setPassword(newPassword);
            workerOperation.update(workerPO);
            return true;
        }

        // If it's a requester
        RequesterPO requesterPO = requesterOperation.getBySingleField("email", email);
        if (requesterPO != null) {
            requesterPO.setPassword(newPassword);
            requesterOperation.update(requesterPO);
            return true;
        }

        // Not found
        return false;
    }

    @Override
    public double changeDollars(String email, double dollars) {

        BigDecimal change = new BigDecimal(dollars);
        BigDecimal balance = new BigDecimal(dollars);

        // If it's a worker
        WorkerPO workerPO = workerOperation.getBySingleField("email", email);
        if (workerPO != null) {
            balance = balance.add(new BigDecimal(workerPO.getDollars()));
            if (balance.compareTo(BigDecimal.ZERO) > 0) {
                workerPO.setDollars(balance.doubleValue());
                return balance.doubleValue();
            }
            return -1;
        }

        // If it's a requester
        RequesterPO requesterPO = requesterOperation.getBySingleField("email", email);
        if (requesterPO != null) {
            balance = balance.add(new BigDecimal(requesterPO.getDollars()));
            if (balance.compareTo(BigDecimal.ZERO) > 0) {
                requesterPO.setDollars(balance.doubleValue());
                return balance.doubleValue();
            }
        }
        // Change failed
        return -1;
    }

    private boolean setWorker(String email, Consumer<WorkerPO> setter) {
        WorkerPO workerPO = workerOperation.getBySingleField("email", email);
        if (workerPO == null)
            return false;

        setter.accept(workerPO);
        workerOperation.update(workerPO);
        return true;
    }

    private boolean setRequester(String email, Consumer<RequesterPO> setter) {
        RequesterPO requesterPO = requesterOperation.getBySingleField("email", email);
        if (requesterPO == null)
            return false;

        setter.accept(requesterPO);
        requesterOperation.update(requesterPO);
        return true;
    }
}

package top.minecode.dao.admin;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.domain.admin.RequesterItem;
import top.minecode.domain.admin.WorkerItem;
import top.minecode.po.requester.RequesterPO;
import top.minecode.po.worker.WorkerPO;

import java.math.BigDecimal;
import java.util.List;

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

        BigDecimal balance = new BigDecimal(dollars);

        // If it's a worker
        WorkerPO workerPO = workerOperation.getBySingleField("email", email);
        if (workerPO != null) {
            balance = balance.add(new BigDecimal(workerPO.getDollars()));
            if (balance.compareTo(BigDecimal.ZERO) > 0) {
                workerPO.setDollars(balance.doubleValue());
                workerOperation.update(workerPO);
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
                requesterOperation.update(requesterPO);
                return balance.doubleValue();
            }
        }

        // Change failed
        return -1;
    }

    @Override
    public List<WorkerItem> getWorkers(int page, int pageSize) {
        String workerPOHql = "select new top.minecode.domain.admin.WorkerItem(t) " +
                " from WorkerPO t order by t.Score desc";

        //noinspection unchecked
        return (List<WorkerItem>) getPage(page, pageSize, workerPOHql);
    }

    @Override
    public List<RequesterItem> getRequester(int page, int pageSize) {
        String hql = "select new top.minecode.domain.admin.RequesterItem(r, cast(count(t) as int)) " +
                " from RequesterPO r, TaskPO t where t.ownerEmail=r.email " +
                " group by r.email order by count(t) desc";

        //noinspection unchecked
        return (List<RequesterItem>) getPage(page, pageSize, hql);
    }

    @Override
    public List<WorkerItem> searchWorkers(String key, int page, int pageSize) {
        String hql = "select new top.minecode.domain.admin.WorkerItem(w) " +
                " from WorkerPO w where w.email like :key or w.name like :key";

        //noinspection unchecked
        return (List<WorkerItem>) searchPage(page, pageSize, hql, key);
    }

    @Override
    public List<RequesterItem> searchRequester(String key, int page, int pageSize) {
        String hql = "select new top.minecode.domain.admin.RequesterItem(r, cast(count(t) as int)) " +
                " from RequesterPO r, TaskPO t where (r.email like :key or r.name like :key) and " +
                " t.ownerEmail=r.email group by r.email";

        //noinspection unchecked
        return (List<RequesterItem>) searchPage(page, pageSize, hql, key);
    }

    private List searchPage(int page, int pageSize, String hql, String key) {
        int start = (page - 1) * pageSize;
        int end = start + pageSize;

        return CommonOperation.template(session -> {
            Query query = session.createQuery(hql);
            query.setFirstResult(start);
            query.setMaxResults(end);
            query.setString("key", "%" + key + "%");
            //noinspection unchecked
            return (List<WorkerItem>) query.list();
        });
    }

    private List getPage(int page, int pageSize, String hql) {
        int start = (page - 1) * pageSize;
        int end = start + pageSize;

        return CommonOperation.template(session -> {
            Query query = session.createQuery(hql);
            query.setFirstResult(start);
            query.setMaxResults(end);
            //noinspection unchecked
            return query.list();
        });
    }
}

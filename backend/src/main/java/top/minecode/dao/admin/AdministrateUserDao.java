package top.minecode.dao.admin;

import top.minecode.domain.admin.RequesterItem;
import top.minecode.domain.admin.WorkerItem;

import java.util.List;

/**
 * Created on 2018/6/2.
 * Description:
 * @author Liao
 */
public interface AdministrateUserDao {

    boolean changePassword(String email, String newPassword);

    /**
     * Change user's dollars, return user's balance if succeed,
     * return -1 if change failed.
     * @param email user's email
     * @param dollars dollars' change value
     * @return balance if change successfully, -1 otherwise
     */
    double changeDollars(String email, double dollars);

    List<WorkerItem> getWorkers(int page, int pageSize);

    List<RequesterItem> getRequester(int page, int pageSize);

    List<WorkerItem> searchWorkers(String key, int page, int pageSize);

    List<RequesterItem> searchRequester(String key, int page, int pageSize);
}

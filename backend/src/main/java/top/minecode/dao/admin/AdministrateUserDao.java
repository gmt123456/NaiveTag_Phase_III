package top.minecode.dao.admin;

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
}

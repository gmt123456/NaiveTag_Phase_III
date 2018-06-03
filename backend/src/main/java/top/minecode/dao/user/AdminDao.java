package top.minecode.dao.user;

/**
 * Created on 2018/5/29.
 * Description:
 * @author Liao
 */
public interface AdminDao {

    boolean hasHighestAuthority(String username);

    boolean exists(String admin);

    boolean addAdmin(String username, String password);

    boolean addStaff(String email, String password);
}

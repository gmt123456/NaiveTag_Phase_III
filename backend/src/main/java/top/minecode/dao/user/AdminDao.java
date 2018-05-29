package top.minecode.dao.user;

/**
 * Created on 2018/5/29.
 * Description:
 * @author Liao
 */
public interface AdminDao {

    boolean checkAuthority(String username);

    boolean addAdmin(String username, String password);
}

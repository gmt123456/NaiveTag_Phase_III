package top.minecode.dao.log;

/**
 * Created on 2018/5/23.
 * Description:
 * @author Liao
 */
public interface AccountLogDao {

    void addLog(String email, double dollars, double balance);
}

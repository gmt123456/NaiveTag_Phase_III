package top.minecode.dao.user;

import top.minecode.domain.user.User;

import java.util.Date;

/**
 * Created on 2018/5/19.
 * Description:
 * @author Liao
 */
public interface UserDao {

    /**
     * Get user by email. You can cast the returned <tt>User</tt>
     * to <tt>Worker</tt> or <tt>Requester</tt> according to the
     * userType. This method will just search in workers and
     * requester, the administrator will not be included.
     * @param email user's email
     * @return user with this email, null otherwise
     */
    User getUser(String email);

    /**
     * Add worker to the database
     * @param email user's email
     * @param pwd user's password
     * @param name user's name(Display name)
     * @param joinTime user's signup time
     * @param avatar worker's avatar's path
     */
    void addWorker(String email, String pwd, String name, Date joinTime, String avatar);

    /**
     * Add requester to the database
     * @param email requester's email
     * @param pwd requester's password
     * @param name requester's name(Display name)
     * @param joinTime requester's signup time
     * @param avatar requester's avatar's path
     */
    void addRequester(String email, String pwd, String name, Date joinTime, String avatar);
}

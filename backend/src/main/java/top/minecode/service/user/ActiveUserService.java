package top.minecode.service.user;

import top.minecode.domain.user.User;

/**
 * Created on 2018/5/19.
 * Description:
 * @author Liao
 */
public interface ActiveUserService {

    /**
     * Get user's email by token
     * @param token user's token
     * @return user's email if this user is still active now, null otherwise
     */
    String getUserMail(String token);

    /**
     * Get a <tt>User</tt> object by token
     * @param token user's token
     * @return <tt>User</tt> if the token is valid, null otherwise
     */
    User getUser(String token);

    /**
     * This method should be invoked when a user try to login.
     * It will generate a token for the user.
     * @param userEmail user's email
     * @return user's token
     */
    String addUser(String userEmail);

    /**
     * Remove the token-email pair from records
     * @param token user's token
     */
    void logoutUser(String token);
}

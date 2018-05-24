package top.minecode.domain.user;

import java.util.Date;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
public class User {

    private final UserType userType;
    private String email;
    private String name;
    private double dollars;
    private Date joinTime;
    private String avatar;
    private double score;

    /**
     * Static factory method to getGson a user with requester user type
     * @param email user's email
     * @param name user's name
     * @param dollars user's dollars
     * @param joinTime user's time when sign up
     * @param avatar path of user's avatar
     * @return <tt>User</tt> object with the attributes passed and
     * <tt>REQUESTER</tt> user type
     */
    public static User requester(String email, String name, double dollars,
                                 Date joinTime, String avatar) {
        return new User(UserType.REQUESTER, email, name, dollars, joinTime, avatar);
    }

    /**
     * Static factory method to getGson a user with worker user type
     * @param email user's email
     * @param name user's name
     * @param dollars user's dollars
     * @param joinTime user's time when sign up
     * @param avatar path of user's avatar
     * @param score worker's score
     * @return <tt>User</tt> object with the attributes passed and
     * <tt>WORKER</tt> user type
     */
    public static User worker(String email, String name, double dollars,
                              Date joinTime, String avatar, double score) {
        return new User(UserType.WORKER, email, name, dollars, joinTime, avatar, score);
    }

    private User(UserType userType, String email, String name,
                double dollars, Date joinTime, String avatar) {
        this.userType = userType;
        this.email = email;
        this.name = name;
        this.dollars = dollars;
        this.joinTime = joinTime;
        this.avatar = avatar;
    }

    private User(UserType userType, String email, String name,
                 double dollars, Date joinTime, String avatar, double score) {
        this(userType, email, name, dollars, joinTime, avatar);
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDollars() {
        return dollars;
    }

    public void setDollars(double dollars) {
        this.dollars = dollars;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}

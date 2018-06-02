package top.minecode.web.admin;

/**
 * Created on 2018/6/2.
 * Description:
 * @author Liao
 */
public class AdminChangeDollarsCommand {

    private String email;  // User's email
    private double dollars;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getDollars() {
        return dollars;
    }

    public void setDollars(double dollars) {
        this.dollars = dollars;
    }
}

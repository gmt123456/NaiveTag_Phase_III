package top.minecode.po.log;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created on 2018/5/17.
 * Description:
 *
 * @author iznauy
 */
@Entity
public class RequesterAccountLogPO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String userEmail;

    private double dollars;

    private double balance;

    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    @Enumerated(EnumType.STRING)
    private ChangeType type;

    public RequesterAccountLogPO(String userEmail, double dollars, double balance, Date time, ChangeType type) {
        this.userEmail = userEmail;
        this.dollars = dollars;
        this.balance = balance;
        this.time = time;
        this.type = type;
    }

    public RequesterAccountLogPO() {}

    public ChangeType getType() {
        return type;
    }

    public void setType(ChangeType type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public double getDollars() {
        return dollars;
    }

    public void setDollars(double dollars) {
        this.dollars = dollars;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public enum ChangeType {
        RECHARGE, // 充值
        RELEASE_TASK,
        ADVERTISEMENT,
        PAY_BACK, // 没用完的钱退回来
        SYSTEM  // 管理员调整的
    }


}

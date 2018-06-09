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
public class WorkerAccountLogPO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String userEmail;

    private double dollars;

    private double balance;

    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    @Enumerated(EnumType.STRING)
    private WorkerAccountChangeType type;

    public WorkerAccountLogPO() {}

    public WorkerAccountLogPO(String userEmail, double dollars,
                              double balance, Date time, WorkerAccountChangeType type) {
        this.userEmail = userEmail;
        this.dollars = dollars;
        this.balance = balance;
        this.time = time;
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

    public WorkerAccountChangeType getType() {
        return type;
    }

    public void setType(WorkerAccountChangeType type) {
        this.type = type;
    }

    public enum WorkerAccountChangeType {
        OUT, // 提现
        PAY // 任务结算
    }

}

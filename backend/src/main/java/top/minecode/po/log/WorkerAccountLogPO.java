package top.minecode.po.log;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created on 2018/5/17.
 * Description:
 *
 * @author iznauy
 */
public class WorkerAccountLogPO implements Serializable {

    private int id;

    private String userEmail;

    private double dollars;

    private double balance;

    private LocalDateTime time;

    private WorkerAccountChangeType type;

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

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
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

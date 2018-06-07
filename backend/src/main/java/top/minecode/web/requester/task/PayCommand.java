package top.minecode.web.requester.task;

import top.minecode.domain.task.PriorityOptions;
import top.minecode.domain.task.TaskRequirement;

/**
 * Created on 2018/5/26.
 * Description:
 * @author Liao
 */
public class PayCommand {

    private String orderId;
    private double dollars;
    private double advertisementDollars;
    private String userEmail;
    private PriorityOptions priorityOptions;
    private String taskRequirement;

    public String getTaskRequirement() {
        return taskRequirement;
    }

    public void setTaskRequirement(String taskRequirement) {
        this.taskRequirement = taskRequirement;
    }

    public PriorityOptions getPriorityOptions() {
        return priorityOptions;
    }

    public void setPriorityOptions(PriorityOptions priorityOptions) {
        this.priorityOptions = priorityOptions;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getDollars() {
        return dollars;
    }

    public void setDollars(double dollars) {
        this.dollars = dollars;
    }

    public double getAdvertisementDollars() {
        return advertisementDollars;
    }

    public void setAdvertisementDollars(double advertisementDollars) {
        this.advertisementDollars = advertisementDollars;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public String toString() {
        return "PayCommand{" +
                "orderId='" + orderId + '\'' +
                ", dollars=" + dollars +
                ", advertisementDollars=" + advertisementDollars +
                ", userEmail='" + userEmail + '\'' +
                ", priorityOptions=" + priorityOptions +
                '}';
    }
}

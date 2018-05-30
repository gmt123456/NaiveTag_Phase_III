package top.minecode.web.requester.task;

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
                '}';
    }
}

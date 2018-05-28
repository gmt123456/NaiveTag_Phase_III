package top.minecode.domain.utils;

/**
 * Created on 2018/5/26.
 * Description:
 * @author Liao
 */
public class PayMessage extends ResultMessage {

    private String orderId;
    private int pictureNum;
    private double payLowerBound;

    PayMessage(String status, String message, String orderId, double payLowerBound, int pictureNum) {
        super(status, message);
        this.orderId = orderId;
        this.payLowerBound = payLowerBound;
        this.pictureNum = pictureNum;
    }

    @Override
    public String toString() {
        return "PayMessage{" +
                "orderId=" + orderId +
                ", pictureNum=" + pictureNum +
                ", payLowerBound=" + payLowerBound +
                '}';
    }
}

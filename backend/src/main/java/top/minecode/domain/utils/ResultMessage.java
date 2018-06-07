package top.minecode.domain.utils;

/**
 * Created on 2018/5/23.
 * Description:
 * @author Liao
 */
public abstract class ResultMessage {

    public static ResultMessage success() {
        return new SimpleResultMessage(SUCCESS, null);
    }

    public static ResultMessage failure(String message) {
        return new SimpleResultMessage(FAILURE, message);
    }

    public static ResultMessage failure() {
        // Return default failure message
        return new SimpleResultMessage(FAILURE, DEFAULT_FAILURE_MESSAGE);
    }

    public static ResultMessage payMessage(String orderId, double lowerBound, int pictureNum) {
        return new PayMessage(SUCCESS, null, orderId, lowerBound, pictureNum);
    }

    public static ResultMessage authenticationSuccess(String webToken) {
        return new AuthenticationResultMessage(null, SUCCESS, webToken);
    }

    public static ResultMessage authenticationSuccess(String webToken, String userType) {
        return new AuthenticationResultMessage(null, SUCCESS, webToken, userType);
    }

    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";
    private static final String DEFAULT_FAILURE_MESSAGE = "Something is wrong";

    private final String status;
    private final String message;

    protected ResultMessage(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean failed() {
        return !status.equals(SUCCESS);
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}

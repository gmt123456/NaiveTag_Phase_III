package top.minecode.domain.utils;

/**
 * Created on 2018/5/23.
 * Description:
 * @author Liao
 */
public class ResultMessage {

    public static ResultMessage success() {
        return new ResultMessage(SUCCESS, null);
    }

    public static ResultMessage failure(String message) {
        return new ResultMessage(FAILURE, message);
    }

    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";

    private final String status;
    private final String message;

    private ResultMessage(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}

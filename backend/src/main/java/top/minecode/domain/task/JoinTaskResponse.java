package top.minecode.domain.task;

/**
 * Created on 2018/5/20.
 * Description:
 *
 * @author iznauy
 */
public class JoinTaskResponse {

    public static final String UNKNOWN_REASON = "unknown error";

    public static final String DONT_FELLOW_RELU = "low division";

    public static final String HAS_ACCEPTED = "has accepted";

    private boolean result;

    private String reason;

    public JoinTaskResponse(boolean result, String reason) {
        this.result = result;
        this.reason = reason;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

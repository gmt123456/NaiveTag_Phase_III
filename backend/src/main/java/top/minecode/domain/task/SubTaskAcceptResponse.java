package top.minecode.domain.task;

/**
 * Created on 2018/5/21.
 * Description:
 *
 * @author iznauy
 */
public class SubTaskAcceptResponse {

    public static final String UN_KNOWN = "unknown";

    private boolean result;

    private String description;

    public SubTaskAcceptResponse(boolean result, String description) {
        this.result = result;
        this.description = description;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

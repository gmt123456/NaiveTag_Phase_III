package top.minecode.domain.user.worker;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
public class WorkerInfoEditResponse {

    public static final String SUCCESS = "success";

    public static final String FAILURE = "failure";

    public static final String INVALID_PASSWORD = "invalid password";

    private String state;

    public WorkerInfoEditResponse(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}

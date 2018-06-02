package top.minecode.domain.task;

/**
 * Created on 2018/6/2.
 * Description:
 *
 * @author iznauy
 */
public class TaskCommitResponse {

    private boolean result;

    public TaskCommitResponse(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}

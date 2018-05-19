package top.minecode.domain.user.worker;

import top.minecode.domain.DateAndValue;

import java.util.List;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
public class WorkerRecentActivity {

    private List<DateAndValue> dollarsChanges;

    private List<DateAndValue> scoreChanges;

    private List<DateAndValue> activity;

    public WorkerRecentActivity() {
    }

    public WorkerRecentActivity(List<DateAndValue> dollarsChanges,
                                List<DateAndValue> scoreChanges, List<DateAndValue> activity) {
        this.dollarsChanges = dollarsChanges;
        this.scoreChanges = scoreChanges;
        this.activity = activity;
    }

    public List<DateAndValue> getDollarsChanges() {
        return dollarsChanges;
    }

    public void setDollarsChanges(List<DateAndValue> dollarsChanges) {
        this.dollarsChanges = dollarsChanges;
    }

    public List<DateAndValue> getScoreChanges() {
        return scoreChanges;
    }

    public void setScoreChanges(List<DateAndValue> scoreChanges) {
        this.scoreChanges = scoreChanges;
    }

    public List<DateAndValue> getActivity() {
        return activity;
    }

    public void setActivity(List<DateAndValue> activity) {
        this.activity = activity;
    }
}

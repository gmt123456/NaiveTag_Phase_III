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

    private List<DateAndValue> dollarChanges;

    private List<DateAndValue> scoreChanges;

    private List<DateAndValue> activity;

    public WorkerRecentActivity() {
    }

    public WorkerRecentActivity(List<DateAndValue> dollarsChanges,
                                List<DateAndValue> scoreChanges, List<DateAndValue> activity) {
        this.dollarChanges = dollarsChanges;
        this.scoreChanges = scoreChanges;
        this.activity = activity;
    }

    public List<DateAndValue> getDollarsChanges() {
        return dollarChanges;
    }

    public void setDollarsChanges(List<DateAndValue> dollarsChanges) {
        this.dollarChanges = dollarsChanges;
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

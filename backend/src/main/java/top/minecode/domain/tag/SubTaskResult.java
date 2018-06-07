package top.minecode.domain.tag;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2018/6/4.
 * Description:
 *
 * @author iznauy
 */
public class SubTaskResult {

    private Map<String, String> results;

    public SubTaskResult() {
        results = new HashMap<>();
    }

    public SubTaskResult(Map<String, String> results) {
        this.results = results;
    }

    public Map<String, String> getResults() {
        return results;
    }

    public void setResults(Map<String, String> results) {
        this.results = results;
    }

    public void extendResult(Map<String, String> newResults) {
        results.putAll(newResults);
    }

}

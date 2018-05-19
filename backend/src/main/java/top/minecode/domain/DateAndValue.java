package top.minecode.domain;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
public class DateAndValue {

    private String date;

    private double value;

    public DateAndValue(String date, double value) {
        this.date = date;
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public double getValue() {
        return value;
    }
}

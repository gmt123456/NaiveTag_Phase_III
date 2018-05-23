package top.minecode.domain.utils;

import java.text.DecimalFormat;
import java.text.Format;

/**
 * Created on 2018/5/23.
 * Description:
 * @author Liao
 */
public class MoneyConverter {

    private static final String PATTERN = "$#,###.##";

    public String convert(double dollars) {
        Format format = new DecimalFormat(PATTERN);
        return format.format(dollars);
    }
}

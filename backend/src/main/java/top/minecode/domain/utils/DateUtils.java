package top.minecode.domain.utils;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtils {

    public static long getDateDiff(Date oldDate, Date newDate, TimeUnit timeUnit) {
        long diffInMills = newDate.getTime() - oldDate.getTime();
        return timeUnit.convert(diffInMills, TimeUnit.MILLISECONDS);
    }

    public static Date min(Date... dates) {
        if (dates != null) {
            Date min = new Date(Instant.MAX.toEpochMilli());
            for (Date date : dates) {
                if (date != null && date.before(min))
                    min = date;
            }

            return min;
        }

        throw new IllegalArgumentException("No date passed in");
    }
}

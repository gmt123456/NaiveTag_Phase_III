package top.minecode.domain.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DateUtils {

    public static long getDateDiff(Date oldDate, Date newDate, TimeUnit timeUnit) {
        long diffInMills = newDate.getTime() - oldDate.getTime();
        return timeUnit.convert(diffInMills, TimeUnit.MILLISECONDS);
    }

    public static LocalDate toLocalDate(Date date) {
        ZoneId zoneId = ZoneId.systemDefault();
        return date.toInstant().atZone(zoneId).toLocalDate();
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

    public static LocalDate min(LocalDate... localDates) {
        // If not empty, find the minimum date
        if (localDates != null) {
            LocalDate min = LocalDate.MAX;
            for (LocalDate curr : localDates) {
                if (curr != null && curr.isBefore(min))
                    min = curr;
            }
            return min;
        }

        // If empty, just return now
        return LocalDate.now();
    }

    public static List<LocalDate> getToNowInterval(LocalDate start) {
        LocalDate end = LocalDate.now().plusDays(1);
        return Stream.iterate(start, d -> d.plusDays(1))
                .limit(ChronoUnit.DAYS.between(start, end))
                .collect(Collectors.toList());
    }

    /**
     * Default version of getToNowInterval, starts from
     * 2018-5-1
     * @return time axis start from 2018-6-6
     */
    public static List<LocalDate> getToNowInterval() {
        LocalDate start = LocalDate.of(2018, 6, 6);
        LocalDate end = LocalDate.now().plusDays(1);
        return Stream.iterate(start, d -> d.plusDays(1))
                .limit(ChronoUnit.DAYS.between(start, end))
                .collect(Collectors.toList());
    }

    public static List<String> formatLocalDateStrings(List<LocalDate> localDates) {
        // Get time list
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDates.stream().map(formatter::format).collect(Collectors.toList());
    }

}

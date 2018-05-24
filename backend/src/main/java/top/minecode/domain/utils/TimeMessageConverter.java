package top.minecode.domain.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

/**
 * Created on 2018/5/23.
 * Description:
 * @author Liao
 */
public class TimeMessageConverter {

    // Fields below are just used to calculate a approximated number, so
    // don't care about it's a leap year or not
    private static final int YEAR = 365;
    private static final int MONTH = 29;
    private static final String[][] TIME_REPRESENTATION;

    static {
        TIME_REPRESENTATION = new String[5][4];
        // Odd number
        TIME_REPRESENTATION[0][0] = "a year ago";
        TIME_REPRESENTATION[1][0] = "one month ago";
        TIME_REPRESENTATION[2][0] = "in the past day";
        TIME_REPRESENTATION[3][0] = "in the past hour";
        TIME_REPRESENTATION[4][0] = "in the past minute";

        // Plural
        TIME_REPRESENTATION[0][1] = "years ago";
        TIME_REPRESENTATION[1][1] = "months ago";
        TIME_REPRESENTATION[2][1] = "days ago";
        TIME_REPRESENTATION[3][1] = "hours ago";
        TIME_REPRESENTATION[4][1] = "minutes ago";

        // To go
        TIME_REPRESENTATION[0][2] = "one year to go";
        TIME_REPRESENTATION[1][2] = "one month to go";
        TIME_REPRESENTATION[2][2] = "one day to go";
        TIME_REPRESENTATION[3][2] = "one hour to go";
        TIME_REPRESENTATION[4][2] = "one minute to go";

        TIME_REPRESENTATION[0][3] = "years to go";
        TIME_REPRESENTATION[1][3] = "months to go";
        TIME_REPRESENTATION[2][3] = "days to go";
        TIME_REPRESENTATION[3][3] = "hours to go";
        TIME_REPRESENTATION[4][3] = "minutes to go";
    }

    /**
     * Convert sign up time and login time to message like
     * 'joined xxx year ago, last seen xxx'
     * @param joinTime        sign up time
     * @param latestLoginTime time of last login
     * @return message described sign up and login
     */
    public String convertBoth(Date joinTime, Date latestLoginTime) {
        String signUpMsg = convertSignUp(joinTime);
        String latestLoginMsg = convertLogin(latestLoginTime);
        return Optional.ofNullable(latestLoginMsg).map(e -> signUpMsg + ", " + e).orElse(signUpMsg);
    }

    /**
     * Convert sign up time to message like
     * 'joined xxx year ago;
     * @param signUpTime sign up time
     * @return message described sign up time
     */
    public String convertSignUp(@NotNull Date signUpTime) {
        Duration signupDuration = getDurationToNow(signUpTime);

        return Optional.ofNullable(convertDuration(signupDuration))
                .map(e -> "joined " + e).orElse("joined just now");
    }

    /**
     * Convert latest login time to message like
     * 'last seen xxx ago;
     * @param latestLoginTime latest login time
     * @return message described latest login time time, or null
     * if the user sign up just now
     */
    public String convertLogin(@Nullable Date latestLoginTime) {
        if (latestLoginTime == null)
            return null;

        Duration loginDuration = getDurationToNow(latestLoginTime);

        return Optional.ofNullable(convertDuration(loginDuration)).map(e -> "last seen " + e).orElse(null);
    }

    public String convertStartTime(@NotNull Date startTime) {
        Duration startToNow = getDurationToNow(startTime);

        return Optional.ofNullable(convertDuration(startToNow))
                .map(e -> "upload " + e).orElse("upload just now");
    }

    public String convertDeadline(@NotNull Date deadline) {
        Duration nowToDeadline = getDurationToNow(deadline);

        return Optional.ofNullable(convertDuration(nowToDeadline))
                .orElse("less than a minute to go");
    }

    private String convertDuration(Duration duration) {
        // Convert to year, month, week, day and minutes
        long[] durations = new long[5];  // Stores year, month, week, day and minutes
        durations[0] = duration.abs().toDays() / YEAR;
        durations[1] = duration.abs().toDays() / MONTH;
        durations[2] = duration.abs().toDays();
        durations[3] = duration.abs().toHours();
        durations[4] = duration.abs().toMinutes();

        // Convert to String representation according to odd or plural
        if (duration.isNegative()) {
            // Convert to "to go" pattern
            for (int i = 0; i < durations.length; i++) {
                if (durations[i] > 0) {
                    long d = durations[i];
                    return durations[i] > 1 ? d + " " + TIME_REPRESENTATION[i][3] : TIME_REPRESENTATION[i][2];
                }
            }
        } else {
            for (int i = 0; i < durations.length; i++) {
                if (durations[i] > 0) {
                    long d = durations[i];
                    return durations[i] > 1 ? d + " " + TIME_REPRESENTATION[i][1] : TIME_REPRESENTATION[i][0];
                }
            }
        }

        return null; // If sign up just now, return null
    }

    private Duration getDurationToNow(Date target) {
        // Convert to LocalDateTime
        Instant instant = target.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime targetTime = instant.atZone(zoneId).toLocalDateTime();
        LocalDateTime now = LocalDateTime.now();
        return Duration.between(targetTime, now);
    }
}

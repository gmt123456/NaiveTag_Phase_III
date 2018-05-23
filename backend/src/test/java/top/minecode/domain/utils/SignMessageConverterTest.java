package top.minecode.domain.utils;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created on 2018/5/23.
 * Description:
 * @author Liao
 */
public class SignMessageConverterTest {

    private SignMessageConverter converter = new SignMessageConverter();

    @Test
    public void testConvertBoth() {
        Date oddSignUpDate = localDateToDate(LocalDateTime.now().minusYears(1));
        Date oddLoginDate = localDateToDate(LocalDateTime.now().minusHours(1));
        Date pluralSignUpDate = localDateToDate(LocalDateTime.now().minusMonths(3));
        Date pluralLoginDate = localDateToDate(LocalDateTime.now().minusMinutes(3));
        assertEquals("joined a year ago, last seen in the past hour",
                converter.convertBoth(oddSignUpDate, oddLoginDate));
        assertEquals("joined 3 months ago, last seen 3 minutes ago",
                converter.convertBoth(pluralSignUpDate, pluralLoginDate));
    }

    @Test
    public void testConvertLogin1() {
        Date oddDate = localDateToDate(LocalDateTime.now().minusHours(1));
        Date pluralDate = localDateToDate(LocalDateTime.now().minusHours(3));
        assertEquals(converter.convertLogin(oddDate), "last seen in the past hour");
        assertEquals(converter.convertLogin(pluralDate), "last seen 3 hours ago");
    }

    @Test
    public void testConvertLogin2() {
        Date oddDate = localDateToDate(LocalDateTime.now().minusDays(1));
        Date pluralDate = localDateToDate(LocalDateTime.now().minusDays(3));
        assertEquals(converter.convertLogin(oddDate), "last seen in the past day");
        assertEquals(converter.convertLogin(pluralDate), "last seen 3 days ago");
    }

    @Test
    public void testConvertLogin3() {
        Date oddDate = localDateToDate(LocalDateTime.now().minusMonths(1));
        Date pluralDate = localDateToDate(LocalDateTime.now().minusMonths(11));
        assertEquals(converter.convertLogin(oddDate), "last seen one month ago");
        assertEquals(converter.convertLogin(pluralDate), "last seen 11 months ago");
    }

    @Test
    public void testConvertBoth1() {
        Date oddDate = localDateToDate(LocalDateTime.now().minusYears(1));
        Date pluralDate = localDateToDate(LocalDateTime.now().minusYears(3));
        assertEquals(converter.convertLogin(oddDate), "last seen a year ago");
        assertEquals(converter.convertLogin(pluralDate), "last seen 3 years ago");
    }

    @Test
    public void testConvertSignUP() {
        Date oddDate = localDateToDate(LocalDateTime.now().minusYears(1));
        Date pluralDate = localDateToDate(LocalDateTime.now().minusYears(3));
        assertEquals(converter.convertSignUp(oddDate), "joined a year ago");
        assertEquals(converter.convertSignUp(pluralDate), "joined 3 years ago");
    }

    private Date localDateToDate(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zoneId).toInstant();
        return Date.from(instant);
    }
}
package top.minecode.domain.utils;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created on 2018/5/23.
 * Description:
 * @author Liao
 */
public class TimeMessageConverterTest {

    @Test
    public void testConvertBoth() {
        Date oddSignUpDate = localDateToDate(LocalDateTime.now().minusYears(1));
        Date oddLoginDate = localDateToDate(LocalDateTime.now().minusHours(1));
        Date pluralSignUpDate = localDateToDate(LocalDateTime.now().minusMonths(3));
        Date pluralLoginDate = localDateToDate(LocalDateTime.now().minusMinutes(3));
        assertEquals("joined a year ago, last seen in the past hour",
                TimeMessageConverter.convertBoth(oddSignUpDate, oddLoginDate));
        assertEquals("joined 3 months ago, last seen 3 minutes ago",
                TimeMessageConverter.convertBoth(pluralSignUpDate, pluralLoginDate));
    }

    @Test
    public void testConvertLogin1() {
        Date oddDate = localDateToDate(LocalDateTime.now().minusHours(1));
        Date pluralDate = localDateToDate(LocalDateTime.now().minusHours(3));
        assertEquals(TimeMessageConverter.convertLogin(oddDate), "last seen in the past hour");
        assertEquals(TimeMessageConverter.convertLogin(pluralDate), "last seen 3 hours ago");
    }

    @Test
    public void testConvertLogin2() {
        Date oddDate = localDateToDate(LocalDateTime.now().minusDays(1));
        Date pluralDate = localDateToDate(LocalDateTime.now().minusDays(3));
        assertEquals(TimeMessageConverter.convertLogin(oddDate), "last seen in the past day");
        assertEquals(TimeMessageConverter.convertLogin(pluralDate), "last seen 3 days ago");
    }

    @Test
    public void testConvertLogin3() {
        Date oddDate = localDateToDate(LocalDateTime.now().minusMonths(1));
        Date pluralDate = localDateToDate(LocalDateTime.now().minusMonths(11));
        assertEquals(TimeMessageConverter.convertLogin(oddDate), "last seen one month ago");
        assertEquals(TimeMessageConverter.convertLogin(pluralDate), "last seen 11 months ago");
    }

    @Test
    public void testConvertLogin4() {
        assertNull(TimeMessageConverter.convertLogin(null));
    }

    @Test
    public void testConvertLogin5() {
        assertNull(TimeMessageConverter.convertLogin(new Date()));
    }

    @Test
    public void testConvertBoth1() {
        Date oddDate = localDateToDate(LocalDateTime.now().minusYears(1));
        Date pluralDate = localDateToDate(LocalDateTime.now().minusYears(3));
        assertEquals(TimeMessageConverter.convertLogin(oddDate), "last seen a year ago");
        assertEquals(TimeMessageConverter.convertLogin(pluralDate), "last seen 3 years ago");
    }

    @Test
    public void testConvertSignUp1() {
        Date oddDate = localDateToDate(LocalDateTime.now().minusYears(1));
        Date pluralDate = localDateToDate(LocalDateTime.now().minusYears(3));
        assertEquals(TimeMessageConverter.convertSignUp(oddDate), "joined a year ago");
        assertEquals(TimeMessageConverter.convertSignUp(pluralDate), "joined 3 years ago");
    }

    @Test
    public void testConvertSignUp2() {
        assertEquals("joined just now", TimeMessageConverter.convertSignUp(new Date()));
    }

    @Test
    public void testConvertStart() {
        assertEquals("upload just now", TimeMessageConverter.convertStartTime(new Date()));
    }

    @Test
    public void testConvertStart2() {
        Date oddDate = localDateToDate(LocalDateTime.now().minusYears(1));
        Date pluralDate = localDateToDate(LocalDateTime.now().minusHours(3));
        assertEquals("upload a year ago", TimeMessageConverter.convertStartTime(oddDate));
        assertEquals("upload 3 hours ago", TimeMessageConverter.convertStartTime(pluralDate));
    }

    @Test
    public void testConvertDDL1() {
        assertEquals("less than a minute to go", TimeMessageConverter.convertDeadline(new Date()));
    }

    @Test
    public void testConvertDDL2() {
        Date oddDate = localDateToDate(LocalDateTime.now().plusYears(1));
        Date pluralDate = localDateToDate(LocalDateTime.now().plusHours(3));
        assertEquals("one year to go", TimeMessageConverter.convertDeadline(oddDate));
        assertEquals("3 hours to go", TimeMessageConverter.convertDeadline(pluralDate));
    }

    private Date localDateToDate(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zoneId).toInstant();
        return Date.from(instant);
    }
}
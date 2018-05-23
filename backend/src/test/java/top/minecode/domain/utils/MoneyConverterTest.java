package top.minecode.domain.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created on 2018/5/23.
 * Description:
 * @author Liao
 */
public class MoneyConverterTest {

    private MoneyConverter converter = new MoneyConverter();

    @Test
    public void test1() {
        assertEquals("$1,123", converter.convert(1123));
    }

    @Test
    public void test2() {
        assertEquals("$1,234,567.23", converter.convert(1234567.2335));
    }
}
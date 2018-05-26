package top.minecode.web.common;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Created on 2018/5/25.
 * Description:
 * @author Liao
 */
@Component
public class StringToLocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String s) {
        return LocalDate.parse(s);
    }
}

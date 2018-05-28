package top.minecode.web.common;

import org.springframework.core.convert.converter.Converter;
import top.minecode.domain.user.worker.Division;

/**
 * Created on 2018/5/26.
 * Description:
 * @author Liao
 */
public class StringToDivisionConverter implements Converter<String, Division> {

    @Override
    public Division convert(String s) {
        s = Character.toUpperCase(s.charAt(0)) + s.toLowerCase().substring(1);
        return Division.valueOf(s);
    }
}

package top.minecode.web.common;

import org.springframework.core.convert.converter.Converter;
import top.minecode.domain.user.admin.AdminAuthority;

/**
 * Created on 2018/5/29.
 * Description:
 * @author Liao
 */
public class StringToAdminAuthorityConverter implements Converter<String, AdminAuthority> {

    @Override
    public AdminAuthority convert(String s) {
        return AdminAuthority.valueOf(s.toUpperCase());
    }
}

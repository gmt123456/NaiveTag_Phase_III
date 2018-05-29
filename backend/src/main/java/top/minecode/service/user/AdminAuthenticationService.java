package top.minecode.service.user;

import top.minecode.domain.utils.ResultMessage;
import top.minecode.web.user.NewAdminCommand;

/**
 * Created on 2018/5/29.
 * Description:
 * @author Liao
 */
public interface AdminAuthenticationService {

    ResultMessage login(String username, String password);

    ResultMessage createNewAdmin(NewAdminCommand command);
}

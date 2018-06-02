package top.minecode.service.admin;

import top.minecode.domain.utils.ResultMessage;
import top.minecode.web.admin.AdminChangeDollarsCommand;
import top.minecode.web.admin.AdminChangePasswordCommand;

/**
 * Created on 2018/6/2.
 * Description:
 * @author Liao
 */
public interface AdminService {

    ResultMessage changePassword(String admin, AdminChangePasswordCommand changePwdCommand);

    ResultMessage changeDollars(String admin, AdminChangeDollarsCommand changeDollarsCommand);
}

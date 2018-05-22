package top.minecode.service.util;

import top.minecode.web.user.SignupCommand;

/**
 * Created on 2018/5/22.
 * Description:
 * @author Liao
 */
public interface Encryptor {

    String encrypt(SignupCommand signupCommand);
}

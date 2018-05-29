package top.minecode.dao.utils;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;
import top.minecode.po.admin.AdminPO;

/**
 * Created on 2018/5/29.
 * Description:
 * @author Liao
 */
public class AdminRealm extends AuthenticatingRealm {

    private CommonOperation<AdminPO> adminOperation = new CommonOperation<>(AdminPO.class);

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();

        AdminPO adminPO = adminOperation.getBySingleField("username", username);
        if (adminPO != null) {
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, adminPO.getPassword(), getName());
            info.setCredentialsSalt(ByteSource.Util.bytes(username));
            return info;
        }

        throw new AuthenticationException();
    }
}

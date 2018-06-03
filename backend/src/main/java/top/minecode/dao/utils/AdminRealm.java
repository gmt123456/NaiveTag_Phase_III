package top.minecode.dao.utils;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;
import top.minecode.po.admin.AdminPO;
import top.minecode.po.admin.StaffPO;

/**
 * Created on 2018/5/29.
 * Description:
 * @author Liao
 */
public class AdminRealm extends AuthenticatingRealm {

    private CommonOperation<AdminPO> adminOperation = new CommonOperation<>(AdminPO.class);
    private CommonOperation<StaffPO> staffOperation = new CommonOperation<>(StaffPO.class);

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String identity = (String) authenticationToken.getPrincipal();

        AdminPO adminPO = adminOperation.getBySingleField("userName", identity);
        if (adminPO != null) {
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(identity, adminPO.getPassword(), getName());
            info.setCredentialsSalt(ByteSource.Util.bytes(identity));
            return info;
        }

        StaffPO staffPO = staffOperation.getBySingleField("email", identity);
        if (staffPO != null) {
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(identity, staffPO.getPassword(), getName());
            info.setCredentialsSalt(ByteSource.Util.bytes(identity));
            return info;
        }

        throw new AuthenticationException();
    }
}

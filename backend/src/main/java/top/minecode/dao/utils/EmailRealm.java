package top.minecode.dao.utils;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;
import top.minecode.po.requester.RequesterPO;
import top.minecode.po.worker.WorkerPO;

/**
 * Created on 2018/5/17.
 * Description:
 * @author Liao
 */
public class EmailRealm extends AuthenticatingRealm {

    private CommonOperation<RequesterPO> requesterOperation = new CommonOperation<>(RequesterPO.class);
    private CommonOperation<WorkerPO> workerOperation = new CommonOperation<>(WorkerPO.class);

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String email = (String) authenticationToken.getPrincipal();

        System.out.println(email);
        // Find worker or requester with the email passed
        WorkerPO target = workerOperation.getBySingleField("email", email);
        if (target != null) {
            // Ground password
            return getInfo(email, target.getPassword());
        }

        RequesterPO requesterTarget = requesterOperation.getBySingleField("email", email);
        if (requesterTarget != null) {
            return getInfo(email, requesterTarget.getPassword());
        }

        throw new AuthenticationException();
    }

    private SimpleAuthenticationInfo getInfo(String email, String groundPwd) {
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(email, groundPwd, getName());
        info.setCredentialsSalt(ByteSource.Util.bytes(email));
        return info;
    }
}

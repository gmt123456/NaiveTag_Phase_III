package top.minecode.domain.user.requester;

import top.minecode.domain.user.User;
import top.minecode.po.log.LoginLogPO;
import top.minecode.po.requester.RequesterPO;

import java.util.Date;

/**
 * Created on 2018/5/23.
 * Description:
 * @author Liao
 */
public class Requester {

    private final String name;
    private final String email;
    private final String signMessage;
    private final String dollars;
    private final String avatar;

    public Requester(RequesterPO po, Date latestLoginTime) {
        name = po.getName();
        email = po.getEmail();
        avatar = po.getAvatar();

        signMessage = transformSignMessage(po.getJoinTime(), latestLoginTime);
        dollars = transferDollars(po.getDollars());
    }

    private String transformSignMessage(Date joinTime, Date latestLoginTime) {

        return null;
    }

    private String transferDollars(double dollars) {
        return null;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getSignMessage() {
        return signMessage;
    }

    public String getDollars() {
        return dollars;
    }

    public String getAvatar() {
        return avatar;
    }
}

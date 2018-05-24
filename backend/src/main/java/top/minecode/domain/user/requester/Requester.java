package top.minecode.domain.user.requester;

import top.minecode.po.requester.RequesterPO;

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

    public Requester(RequesterPO po, String signMessage, String dollars) {
        name = po.getName();
        email = po.getEmail();
        avatar = po.getAvatar();
        this.signMessage = signMessage;
        this.dollars = dollars;
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

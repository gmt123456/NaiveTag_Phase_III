package top.minecode.web.requester.info;

import top.minecode.po.requester.RequesterPO;

/**
 * Created on 2018/5/26.
 * Description:
 * @author Liao
 */
public class ChangeNameCommand implements ChangeCommand<RequesterPO> {

    private String name;

    @Override
    public void change(RequesterPO po) throws Exception {
        po.setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

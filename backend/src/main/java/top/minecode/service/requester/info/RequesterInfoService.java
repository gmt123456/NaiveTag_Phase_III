package top.minecode.service.requester.info;

import top.minecode.domain.utils.ResultMessage;
import top.minecode.po.requester.RequesterPO;
import top.minecode.web.requester.info.*;

/**
 * Created on 2018/5/23.
 * Description:
 * @author Liao
 */
public interface RequesterInfoService {

    String getMainInfo(String email);

    String getAccountInfo(String email, PageCommand pageCommand);

    String recharge(String email, double dollars);

    ResultMessage changeInfo(String email, ChangeCommand<RequesterPO> command);
}

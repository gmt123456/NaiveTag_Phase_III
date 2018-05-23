package top.minecode.service.requester.info;

import org.springframework.stereotype.Service;
import top.minecode.web.requester.info.ChangeInfoCommand;
import top.minecode.web.requester.info.PageCommand;

/**
 * Created on 2018/5/23.
 * Description:
 * @author Liao
 */
public interface RequesterInfoService {

    String getMainInfo(String email);

    String getAccountInfo(String email, PageCommand pageCommand);

    String recharge(String email, double dollars);

    String changeInfo(String email, ChangeInfoCommand changeInfoCommand);
}

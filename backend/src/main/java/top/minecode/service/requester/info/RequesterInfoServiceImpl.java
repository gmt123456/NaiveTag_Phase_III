package top.minecode.service.requester.info;

import org.springframework.stereotype.Service;
import top.minecode.web.requester.info.ChangeInfoCommand;
import top.minecode.web.requester.info.PageCommand;

/**
 * Created on 2018/5/23.
 * Description:
 * @author Liao
 */
@Service("requesterInfoService")
public class RequesterInfoServiceImpl implements RequesterInfoService {

    @Override
    public String getMainInfo(String email) {
        return null;
    }

    @Override
    public String getAccountInfo(String email, PageCommand pageCommand) {
        return null;
    }

    @Override
    public String recharge(String email, double dollars) {
        return null;
    }

    @Override
    public String changeInfo(String email, ChangeInfoCommand changeInfoCommand) {
        return null;
    }
}

package top.minecode.dao.requester.info;

import top.minecode.domain.user.requester.Requester;
import top.minecode.domain.utils.ResultMessage;
import top.minecode.web.requester.info.ChangeInfoCommand;

/**
 * Created on 2018/5/23.
 * Description:
 * @author Liao
 */
public interface RequesterInfoDao {

    Requester getRequester(String email);

    ResultMessage updateRequesterInfo(ChangeInfoCommand changeInfo, String email);

    ResultMessage updateAccount(String email, double dollars);
}

package top.minecode.dao.requester.info;

import top.minecode.domain.user.requester.Requester;

/**
 * Created on 2018/5/23.
 * Description:
 * @author Liao
 */
public interface RequesterInfoDao {

    Requester getRequester(String email);
}

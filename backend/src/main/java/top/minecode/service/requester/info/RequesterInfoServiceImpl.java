package top.minecode.service.requester.info;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.minecode.dao.requester.info.RequesterInfoDao;
import top.minecode.domain.user.requester.AccountLog;
import top.minecode.domain.user.requester.Requester;
import top.minecode.domain.utils.ResultMessage;
import top.minecode.po.requester.RequesterPO;
import top.minecode.service.util.Encryptor;
import top.minecode.web.requester.info.*;

import java.util.List;

/**
 * Created on 2018/5/23.
 * Description:
 * @author Liao
 */
@Service("requesterInfoService")
public class RequesterInfoServiceImpl implements RequesterInfoService {

    private RequesterInfoDao infoDao;
    private Gson gson;
    private Encryptor encryptor;

    @Autowired
    public void setEncryptor(Encryptor encryptor) {
        this.encryptor = encryptor;
    }

    @Autowired
    public void setGson(Gson gson) {
        this.gson = gson;
    }

    @Autowired
    public void setInfoDao(RequesterInfoDao infoDao) {
        this.infoDao = infoDao;
    }

    @Override
    public String getMainInfo(String email) {
        Requester requester = infoDao.getRequester(email);
        return gson.toJson(requester);
    }

    @Override
    public String getAccountInfo(String email, PageCommand pageCommand) {
        List<AccountLog> logs = infoDao.getAccountLogs(email, pageCommand.getPage(), pageCommand.getPageSize());
        return gson.toJson(logs);
    }

    @Override
    public String recharge(String email, double dollars) {
        ResultMessage resultMessage = infoDao.updateAccount(email, dollars);
        return gson.toJson(resultMessage);
    }

    @Override
    public ResultMessage changeInfo(String email, ChangeCommand<RequesterPO> command) {
        return infoDao.updateRequesterInfo(command, email);
    }

}

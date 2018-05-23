package top.minecode.service.requester.info;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.minecode.dao.requester.info.RequesterInfoDao;
import top.minecode.domain.user.requester.Requester;
import top.minecode.domain.utils.ResultMessage;
import top.minecode.web.requester.info.ChangeInfoCommand;
import top.minecode.web.requester.info.PageCommand;

import java.math.BigDecimal;

/**
 * Created on 2018/5/23.
 * Description:
 * @author Liao
 */
@Service("requesterInfoService")
public class RequesterInfoServiceImpl implements RequesterInfoService {

    private RequesterInfoDao infoDao;
    private Gson gson;

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
        return null;
    }

    @Override
    public String recharge(String email, double dollars) {
        return null;
    }

    @Override
    public String changeInfo(String email, ChangeInfoCommand changeInfoCommand) {
        ResultMessage resultMessage = infoDao.updateRequesterInfo(changeInfoCommand, email);
        return gson.toJson(resultMessage);
    }
}

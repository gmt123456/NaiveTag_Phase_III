package top.minecode.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.minecode.dao.admin.AdministrateUserDao;
import top.minecode.dao.log.AccountLogDao;
import top.minecode.dao.user.AdminDao;
import top.minecode.domain.admin.RequesterItem;
import top.minecode.domain.admin.WorkerItem;
import top.minecode.domain.utils.ResultMessage;
import top.minecode.po.log.RequesterAccountLogPO;
import top.minecode.service.util.Encryptor;
import top.minecode.web.admin.AdminChangeDollarsCommand;
import top.minecode.web.admin.AdminChangePasswordCommand;
import top.minecode.web.requester.info.PageCommand;

import java.util.List;

/**
 * Created on 2018/6/2.
 * Description:
 * @author Liao
 */
@Service("adminServiceImpl")
public class AdminServiceImpl implements AdminService {

    private AdministrateUserDao administrateUserDao;
    private AccountLogDao accountLogDao;
    private Encryptor encryptor;

    @Autowired
    public void setEncryptor(Encryptor encryptor) {
        this.encryptor = encryptor;
    }

    @Autowired
    public void setAdministrateUserDao(AdministrateUserDao administrateUserDao) {
        this.administrateUserDao = administrateUserDao;
    }

    @Autowired
    public void setAccountLogDao(AccountLogDao accountLogDao) {
        this.accountLogDao = accountLogDao;
    }

    @Override
    public ResultMessage changePassword(String admin, AdminChangePasswordCommand changePwdCommand) {
        String email = changePwdCommand.getEmail();
        String password = encryptor.encrypt(changePwdCommand.getNewPassword(), email);

        if (administrateUserDao.changePassword(email, password))
            return ResultMessage.success();

        return ResultMessage.failure();
    }

    @Override
    public List<WorkerItem> getWorkers(PageCommand pageCommand) {
        return administrateUserDao.getWorkers(pageCommand.getPage(), pageCommand.getPageSize());
    }

    @Override
    public List<RequesterItem> getRequester(PageCommand pageCommand) {
        return administrateUserDao.getRequester(pageCommand.getPage(), pageCommand.getPageSize());
    }

    @Override
    public List<WorkerItem> searchWorker(int page, int pageSize, String key) {
        return administrateUserDao.searchWorkers(key, page, pageSize);
    }

    @Override
    public List<RequesterItem> searchRequester(int page, int pageSize, String key) {
        return administrateUserDao.searchRequester(key, page, pageSize);
    }

    @Override
    public ResultMessage changeDollars(String admin, AdminChangeDollarsCommand changeDollarsCommand) {
        String email = changeDollarsCommand.getEmail();
        double dollars = changeDollarsCommand.getDollars();

        double balance = administrateUserDao.changeDollars(email, dollars);
        if (balance < 0) {
            // Change failed
            return ResultMessage.failure();
        }

        // Add log
        accountLogDao.log(email, dollars, balance, RequesterAccountLogPO.ChangeType.SYSTEM);
        return ResultMessage.success();
    }
}

package top.minecode.service.admin;

import top.minecode.domain.admin.RequesterItem;
import top.minecode.domain.admin.WorkerItem;
import top.minecode.domain.statistic.ChartData;
import top.minecode.domain.utils.ResultMessage;
import top.minecode.web.admin.AdminChangeDollarsCommand;
import top.minecode.web.admin.AdminChangePasswordCommand;
import top.minecode.web.requester.info.PageCommand;

import java.util.List;

/**
 * Created on 2018/6/2.
 * Description:
 * @author Liao
 */
public interface AdminService {

    ResultMessage changePassword(String admin, AdminChangePasswordCommand changePwdCommand);

    List<WorkerItem> searchWorker(int page, int pageSize, String key);

    List<RequesterItem> searchRequester(int page, int pageSize, String key);

    ResultMessage changeDollars(String admin, AdminChangeDollarsCommand changeDollarsCommand);

    List<WorkerItem> getWorkers(PageCommand pageCommand);

    List<RequesterItem> getRequester(PageCommand pageCommand);

    ChartData getWorkerStatistic(String email);

    ChartData getRequesterStatistic(String email);
}

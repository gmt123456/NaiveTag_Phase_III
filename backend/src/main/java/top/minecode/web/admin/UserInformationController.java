package top.minecode.web.admin;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.minecode.domain.admin.RequesterItem;
import top.minecode.domain.admin.WorkerItem;
import top.minecode.domain.user.UserType;
import top.minecode.domain.utils.ResultMessage;
import top.minecode.service.admin.AdminService;
import top.minecode.web.common.BaseController;
import top.minecode.web.requester.info.PageCommand;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created on 2018/6/2.
 * Description:
 * @author Liao
 */
@Controller
@RequestMapping("admin")
public class UserInformationController extends BaseController {

    private AdminService adminService;
    private Gson gson;

    @Autowired
    public void setGson(Gson gson) {
        this.gson = gson;
    }

    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    @RequestMapping("/workers")
    @ResponseBody
    public String getWorkers(PageCommand command) {
        return gson.toJson(adminService.getWorkers(command));
    }

    @RequestMapping("/requester")
    @ResponseBody
    public String getRequester(PageCommand command) {
        return gson.toJson(adminService.getRequester(command));
    }

    @RequestMapping("/searchUser")
    @ResponseBody
    public String searchUser(SearchCommand command) {
        UserType userType = UserType.valueOf(command.getUserType().toUpperCase());
        if (userType == UserType.REQUESTER) {
            List<RequesterItem> requesterItems =
                    adminService.searchRequester(command.getPage(), command.getPageSize(), command.getKey());
            return gson.toJson(requesterItems);
        }
        else if (userType == UserType.WORKER) {
            List<WorkerItem> workerItems =
                    adminService.searchWorker(command.getPage(), command.getPageSize(), command.getKey());
            return gson.toJson(workerItems);
        }

        throw new IllegalArgumentException("Illegal user type");
    }
    @RequestMapping("/modify/password")
    @ResponseBody
    public String changePwd(HttpServletRequest request, AdminChangePasswordCommand changePwdCommand) {
        return gson.toJson(adminService.changePassword(getAdmin(request), changePwdCommand));
    }

    @RequestMapping("/modify/dollars")
    @ResponseBody
    public String changeDollars(HttpServletRequest request, AdminChangeDollarsCommand changeDollarsCommand) {
        ResultMessage resultMessage = adminService.changeDollars(getAdmin(request), changeDollarsCommand);
        return gson.toJson(resultMessage);
    }
}

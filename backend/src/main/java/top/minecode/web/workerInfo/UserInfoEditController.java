package top.minecode.web.workerInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import top.minecode.domain.user.worker.WorkerInfoEditResponse;
import top.minecode.service.workerInfo.WorkerInfoEditService;
import top.minecode.web.common.BaseController;
import top.minecode.web.common.WebConfig;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
@Controller
@RequestMapping(value = "/worker/userInfo")
public class UserInfoEditController extends BaseController {

    private WorkerInfoEditService service;

    public WorkerInfoEditService getService() {
        return service;
    }

    @Autowired
    public void setService(WorkerInfoEditService service) {
        this.service = service;
    }

    @RequestMapping("/editBasicInfo")
    @ResponseBody
    public String editUserBasicInfo(HttpServletRequest request, String userName) {
        String email = getUserEmail(request);
        return WebConfig.getGson().toJson(service.editBasicInfo(email, userName));
    }

    @RequestMapping("/editAvatar")
    @ResponseBody
    public String editAvatar(HttpServletRequest request, @RequestParam String avatar) {
        String email = getUserEmail(request);
        String[] tempData = avatar.split("base64,");
        String suffix = "";
        if ("data:image/jpeg;".equalsIgnoreCase(tempData[0])) {
            suffix = ".jpg";
        } else if ("data:image/x-icon;".equalsIgnoreCase(tempData[0])) {
            suffix = ".ico";
        } else if ("data:image/gif;".equalsIgnoreCase(tempData[0])) {
            suffix = ".gif";
        } else {
            return WebConfig.getGson().toJson(new WorkerInfoEditResponse(WorkerInfoEditResponse.FAILURE));
        }
        return WebConfig.getGson().toJson(service.editAvatar(email, tempData[1], suffix));
    }

    @RequestMapping(value = "/editPassword")
    @ResponseBody
    public String editPassword(HttpServletRequest request, String oldPassword, String newPassword) {
        String email = getUserEmail(request);
        return WebConfig.getGson().toJson(service.editPassword(email, oldPassword, newPassword));
    }

}

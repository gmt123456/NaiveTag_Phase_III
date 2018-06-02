package top.minecode.web.staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.minecode.domain.tag.TagResult;
import top.minecode.service.staff.StaffSubTaskCheckService;
import top.minecode.web.common.WebConfig;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 2018/6/2.
 * Description:
 *
 * @author iznauy
 */
@Controller
@RequestMapping(value = "/staff/subCheck")
public class StaffSubTaskCheckController {

    private StaffSubTaskCheckService subTaskCheckService;

    public StaffSubTaskCheckService getSubTaskCheckService() {
        return subTaskCheckService;
    }

    @Autowired
    public void setSubTaskCheckService(StaffSubTaskCheckService subTaskCheckService) {
        this.subTaskCheckService = subTaskCheckService;
    }

    @RequestMapping(value = "/first")
    @ResponseBody
    public String getFirstUrl(HttpServletRequest request, int participationId) {
        return subTaskCheckService.morePic(participationId);
    }

    @RequestMapping(value = "/more")
    @ResponseBody // 如果是一个空值的话，那么这个任务就算是完成了
    public String more(HttpServletRequest request, int participationId) {
        return subTaskCheckService.morePic(participationId);
    }

    @RequestMapping(value = "/label")
    @ResponseBody
    public String getLabelInfo(HttpServletRequest request, int participationId, String url) {
        TagResult result = subTaskCheckService.getLabelInfo(participationId, url);
        return WebConfig.getGson().toJson(result, TagResult.class);
    }

    @RequestMapping(value = "/mark")
    @ResponseBody
    public String mark(HttpServletRequest request, int participationId, String url, boolean accept) {
        subTaskCheckService.mark(participationId, url, accept);
        return "ok";
    }
}

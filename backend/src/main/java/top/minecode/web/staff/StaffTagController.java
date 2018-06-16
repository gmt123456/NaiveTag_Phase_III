package top.minecode.web.staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.minecode.domain.tag.TagResult;
import top.minecode.domain.task.TaskType;
import top.minecode.service.tag.TagService;
import top.minecode.web.common.BaseController;
import top.minecode.web.common.WebConfig;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 2018/6/1.
 * Description:
 *
 * @author iznauy
 */
@Controller
@RequestMapping("/staff/tag")
public class StaffTagController extends BaseController {

    private TagService tagService;

    public TagService getTagService() {
        return tagService;
    }

    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    @RequestMapping("/save")
    @ResponseBody
    private void save(HttpServletRequest request, int taskId, int subTaskId, int taskType, String url, String data) {
        TagResult tagResult = WebConfig.getGson().fromJson(data, TagResult.class);
        String email = getStaffEmail(request);
        TaskType type = TaskType.convert(taskType);
        tagService.save(email, taskId, subTaskId, type, url, tagResult);
    }

    @RequestMapping("/next")
    @ResponseBody
    public String next(HttpServletRequest request, int taskId, int subTaskId, int taskType, String url) {
        TaskType type = TaskType.convert(taskType);
        return WebConfig.getGson().toJson(tagService.next(taskId, subTaskId, type, url));
    }

    @RequestMapping("/getLabelInfo")
    @ResponseBody
    public String getLabelInformation(HttpServletRequest request, int taskId, int taskType, int subTaskId, String url) {
        TaskType type = TaskType.convert(taskType);
        String staffEmail = getStaffEmail(request);
        TagResult result = tagService.getLabelInformation(staffEmail, taskId, subTaskId, type, url);

        return WebConfig.getGson().toJson(result, TagResult.class);
    }

    @RequestMapping("/previous")
    @ResponseBody
    public String previous(HttpServletRequest request, int taskId, int subTaskId, int taskType, String url) {
        TaskType type = TaskType.convert(taskType);
        return WebConfig.getGson().toJson(tagService.previous(taskId, subTaskId, type, url));
    }

    @RequestMapping("/taskInfo")
    @ResponseBody
    public String getTaskInformation(HttpServletRequest request, int taskId, int taskType) {
        TaskType type = TaskType.convert(taskType);
        return WebConfig.getGson().toJson(tagService.getTaskInformation(taskId, type));

    }

}

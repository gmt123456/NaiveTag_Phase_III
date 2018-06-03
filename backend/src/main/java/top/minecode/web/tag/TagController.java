package top.minecode.web.tag;

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
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
@Controller
@RequestMapping("/worker/tag")
public class TagController extends BaseController {

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
    public String save(HttpServletRequest request, int taskId, int subTaskId, int taskType, String url, String data) {
        TagResult tagResult = WebConfig.getGson().fromJson(data, TagResult.class);
        System.out.println(WebConfig.getGson().toJson(tagResult));
        String userEmail = getUserEmail(request);
        TaskType type = TaskType.convert(taskType);
        tagService.save(userEmail, taskId, subTaskId, type, url, tagResult);
        return "";
    }

    @RequestMapping("/next")
    @ResponseBody
    public String next(HttpServletRequest request, int taskId, int subTaskId, int taskType, String url) {
        TaskType type = TaskType.convert(taskType);
        // notice the active user table
        return WebConfig.getGson().toJson(tagService.next(taskId, subTaskId, type, url));
    }

    @RequestMapping("/getLabelInfo")
    @ResponseBody
    public String getLabelInformation(HttpServletRequest request, int taskId, int taskType, int subTaskId, String url) {
        String userEmail = getUserEmail(request);
        TaskType type = TaskType.convert(taskType);
        TagResult result = tagService.getLabelInformation(userEmail, taskId, subTaskId, type, url);
        return WebConfig.getGson().toJson(result);
    }

    @RequestMapping("/previous")
    @ResponseBody
    public String previous(HttpServletRequest request, int taskId, int subTaskId, int taskType, String url) {
        TaskType type = TaskType.convert(taskType);
        // notice the active user table
        return WebConfig.getGson().toJson(tagService.previous(taskId, subTaskId, type, url));
    }

    @RequestMapping("/taskInfo")
    @ResponseBody
    public String getTaskInformation(HttpServletRequest request, int taskId, int taskType) {
            TaskType type = TaskType.convert(taskType);
            // notice the active user table
            return WebConfig.getGson().toJson(tagService.getTaskInformation(taskId, type));

    }

}

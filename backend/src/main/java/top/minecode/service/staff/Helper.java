package top.minecode.service.staff;

import top.minecode.domain.tag.GlobalLabelTagResult;
import top.minecode.domain.tag.TagResult;
import top.minecode.domain.task.TaskType;
import top.minecode.po.worker.SubTaskParticipationPO;
import top.minecode.service.util.HttpHelper;
import top.minecode.service.util.PathUtil;
import top.minecode.web.common.WebConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2018/6/16.
 * Description:
 *
 * @author iznauy
 */
class Helper {

    static void updateModel(SubTaskParticipationPO participationPO) {
        if (participationPO.getSubTaskType() != TaskType.t_100)
            return;
        Map<String, String> tagResults = participationPO.getTags();
        StringBuilder buffer = new StringBuilder();
        for (String logicalUrl: tagResults.keySet()) {
            GlobalLabelTagResult result = (GlobalLabelTagResult) WebConfig.getGson().fromJson(tagResults.get(logicalUrl), TagResult.class);
            String label = result.getLabel();
            buffer.append(PathUtil.convertToAbsolutePath(logicalUrl))
                    .append(" ")
                    .append(label)
                    .append("\n");
        }
        String content = buffer.toString();
        try {
            String filePath = PathUtil.getBasePath() + PathUtil.getSubTaskResultPath()
                    + participationPO.getId() + "_" + participationPO.getSubTaskId() + ".txt";
            File resultFile = new File(filePath);
            File parentFile = resultFile.getParentFile();
            if (!parentFile.exists())
                parentFile.mkdir();
            resultFile.createNewFile();
            PrintWriter writer = new PrintWriter(new FileOutputStream(resultFile));
            writer.write(content);

            writer.close();

            Map<String, String> params = new HashMap<>();
            params.put("task_id", String.valueOf(participationPO.getTaskId()));
            params.put("tag_file_path", filePath);

            String url = PathUtil.getPythonServerPath();
            String param = HttpHelper.urlEncode(params);
            HttpHelper.send(url, param);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

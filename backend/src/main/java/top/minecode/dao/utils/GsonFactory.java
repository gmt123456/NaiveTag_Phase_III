package top.minecode.dao.utils;

import com.google.gson.*;
import top.minecode.domain.task.requester.TaskItem;
import top.minecode.domain.user.requester.AccountLog;
import top.minecode.domain.utils.TimeMessageConverter;

/**
 * Created on 2018/5/23.
 * Description:
 * @author Liao
 */
public class GsonFactory {

    private static Gson defaultGson = new GsonBuilder().serializeNulls().create();

    public static Gson create() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(AccountLog.class, new AccountLog.AccountLogSerializer())
                .registerTypeAdapter(TaskItem.class, taskItemSerializer())
                .setPrettyPrinting()
                .serializeNulls();
        return builder.create();
    }

    private static JsonSerializer<TaskItem> taskItemSerializer() {
        return (taskItem, type, jsonSerializationContext) -> {
            JsonElement element = defaultGson.toJsonTree(taskItem);
            JsonObject object = element.getAsJsonObject();
            // Remove start date and end date
            object.remove("begin");
            object.remove("deadline");
            object.remove("dollars");

            // Serialize time information
            TimeMessageConverter messageConverter = new TimeMessageConverter();
            String timeInfo = messageConverter.convertStartTime(taskItem.getBegin()) + ", ";
            timeInfo += messageConverter.convertDeadline(taskItem.getDeadline());
            object.addProperty("timeInfo", timeInfo);

            // Serialize money information
            object.addProperty("dollars", taskItem.getDollars());
            return object;
        };
    }
}

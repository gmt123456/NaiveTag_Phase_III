package top.minecode.web.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import top.minecode.domain.DateAndValue;
import top.minecode.domain.DateAndValueSerializer;
import top.minecode.domain.tag.TagDeserializer;
import top.minecode.domain.tag.TagResult;
import top.minecode.domain.tag.TagSerializer;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
public class WebConfig {

    private static Gson gson;

    static {
        gson = new GsonBuilder()
                .serializeNulls()
                .registerTypeAdapter(TagResult.class, new TagSerializer())
                .registerTypeAdapter(TagResult.class, new TagDeserializer())
                .registerTypeAdapter(DateAndValue.class, new DateAndValueSerializer())
                .create();
    }

    public static Gson getGson() {
        return gson;
    }
}

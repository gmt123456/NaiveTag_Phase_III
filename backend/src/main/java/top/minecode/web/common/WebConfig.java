package top.minecode.web.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
                .setPrettyPrinting()
                .registerTypeAdapter(TagResult.class, new TagSerializer())
                .registerTypeAdapter(TagResult.class, new TagDeserializer())
                .create();
    }

    public static Gson getGson() {
        return gson;
    }
}

package top.minecode.dao.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import top.minecode.domain.tag.TagDeserializer;
import top.minecode.domain.tag.TagResult;
import top.minecode.domain.tag.TagSerializer;

/**
 * Created on 2018/5/21.
 * Description:
 *
 * @author iznauy
 */
public class DaoConfig {

    private static Gson gson;

    static {
        gson = new GsonBuilder()
                .registerTypeAdapter(TagResult.class, new TagSerializer())
                .registerTypeAdapter(TagResult.class, new TagDeserializer())
                .create();
    }

    public static Gson getGson() {
        return gson;
    }

}

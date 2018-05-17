package top.minecode.domain.tag;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2018/4/13.
 * Description:
 *
 * @author iznauy
 */
public class TagSerializer implements JsonSerializer<TagResult> {

    @Override
    public JsonElement serialize(TagResult tagResult, Type type,
                                 JsonSerializationContext jsonSerializationContext) {
        TagType tagType = tagResult.getTagType();
        if (tagType.equals(TagType.t_200) || tagType.equals(TagType.t_201)) {
            SingleSquareTagResult singleSquareTagResult = (SingleSquareTagResult) tagResult;
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("tagType", jsonSerializationContext.serialize(tagType));
            List<Frame> frames = new ArrayList<>();
            frames.add(singleSquareTagResult.getFrame());
            jsonObject.add("frames", jsonSerializationContext.serialize(frames));
            return jsonObject;
        }
        return jsonSerializationContext.serialize(tagResult);
    }


}

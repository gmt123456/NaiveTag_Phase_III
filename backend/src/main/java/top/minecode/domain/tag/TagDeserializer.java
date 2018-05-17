package top.minecode.domain.tag;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created on 2018/4/13.
 * Description:
 *
 * @author iznauy
 */
public class TagDeserializer implements JsonDeserializer<TagResult> {

    @Override
    public TagResult deserialize(JsonElement jsonElement, Type type,
                                 JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject originalObject = jsonElement.getAsJsonObject();
        TagType tagType = jsonDeserializationContext.deserialize(originalObject.get("tagType"), TagType.class);
        TagResult tagResult = null;

        if (tagType.equals(TagType.t_100) || tagType.equals(TagType.t_101)) {
            GlobalTagResult globalTagResult;

            // load data
            String label = originalObject.get("label").getAsString();

            // generate object
            if (tagType.equals(TagType.t_100))
                globalTagResult = new GlobalLabelTagResult(label);
            else
                globalTagResult = new GlobalAnnotationTagResult(label);

            tagResult = globalTagResult;
        } else if (tagType.equals(TagType.t_200) || tagType.equals(TagType.t_201)) {
            SingleSquareTagResult singleSquareTagResult;

            //load data
            Type frameType = new TypeToken<List<Frame>>() {}.getType();
            List<Frame> frames = jsonDeserializationContext.deserialize(originalObject.get("frames").getAsJsonArray(),
                   frameType);
            Frame frame = frames.get(0); // only one frame

            // generate object
            if (tagType.equals(TagType.t_200))
                singleSquareTagResult = new SingleSquareLabelTagResult(frame);
            else
                singleSquareTagResult = new SingleSquareAnnotationTagResult(frame);

            tagResult = singleSquareTagResult;
        } else if (tagType.equals(TagType.t_300) || tagType.equals(TagType.t_301)) {
            MultiSquareTagResult multiSquareTagResult;

            //load data
            Type framesType = new TypeToken<List<Frame>>() {}.getType();
            List<Frame> frames = jsonDeserializationContext.deserialize(originalObject.get("frames").getAsJsonArray(),
                    framesType);

            if (tagType.equals(TagType.t_300))
                multiSquareTagResult = new MultiSquareLabelTagResult(frames);
            else
                multiSquareTagResult = new MultiSquareAnnotationTagResult(frames);

            tagResult = multiSquareTagResult;
        } else if (tagType.equals(TagType.t_400) || tagType.equals(TagType.t_401)) {
            AreaTagResult areaTagResult;

            //load public data
            Type pointList = new TypeToken<List<Point>>() {}.getType();
            List<Point> points = jsonDeserializationContext.deserialize(originalObject.get("points").getAsJsonArray(),
                    pointList);

            if (tagType.equals(TagType.t_400))
                areaTagResult = new SimpleAreaTagResult(points);
            else {
                String label = originalObject.get("label").getAsString();
                areaTagResult = new AreaLabelTagResult(points, label);
            }

            tagResult = areaTagResult;
        }
        
        return tagResult;
    }
}

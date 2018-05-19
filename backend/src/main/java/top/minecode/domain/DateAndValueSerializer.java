package top.minecode.domain;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
public class DateAndValueSerializer implements JsonSerializer<DateAndValue> {

    @Override
    public JsonElement serialize(DateAndValue dateAndValue, Type type, JsonSerializationContext jsonSerializationContext) {
        final JsonObject jsonObject = new JsonObject();
        Date date = dateAndValue.getDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        jsonObject.addProperty("date", "" + year + "-" + month + "-" + day);
        jsonObject.addProperty("value", dateAndValue.getValue());
        return jsonObject;
    }
}


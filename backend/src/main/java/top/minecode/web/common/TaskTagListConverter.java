package top.minecode.web.common;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import top.minecode.domain.task.TaskTag;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created on 2018/5/25.
 * Description:
 * @author Liao
 */
@Component
public class TaskTagListConverter implements Converter<String, List<TaskTag>> {

    private Gson gson;

    @Autowired
    public void setGson(Gson gson) {
        this.gson = gson;
    }

    @Override
    public List<TaskTag> convert(String s) {
        Type type = new TypeToken<List<TaskTag>>(){}.getType();
        return gson.fromJson(s, type);
    }
}

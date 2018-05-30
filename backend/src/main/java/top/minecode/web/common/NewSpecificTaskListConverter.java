package top.minecode.web.common;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import top.minecode.web.requester.task.NewTaskCommand.NewSpecificTask;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created on 2018/5/29.
 * Description:
 * @author Liao
 */
public class NewSpecificTaskListConverter implements Converter<String, List<NewSpecificTask>> {

    private Gson gson;

    @Autowired
    public void setGson(Gson gson) {
        this.gson = gson;
    }

    @Override
    public List<NewSpecificTask> convert(String s) {
        System.out.println("-------specific task converter]----------");
        Type type = new TypeToken<List<NewSpecificTask>>(){}.getType();
        return gson.fromJson(s, type);
    }
}

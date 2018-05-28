package top.minecode.web.common;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import top.minecode.domain.task.TaskTag;

/**
 * Created on 2018/5/25.
 * Description:
 * @author Liao
 */
@Component
public class StringToTaskTagConverter implements Converter<String, TaskTag> {
    @Override
    public TaskTag convert(String s) {
        return TaskTag.valueOf(s);
    }
}

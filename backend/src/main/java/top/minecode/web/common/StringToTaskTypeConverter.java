package top.minecode.web.common;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import top.minecode.domain.task.TaskType;

/**
 * Created on 2018/5/25.
 * Description:
 * @author Liao
 */
@Component
public class StringToTaskTypeConverter implements Converter<Integer, TaskType> {

    @Override
    public TaskType convert(Integer integer) {
        return TaskType.convert(integer);
    }
}

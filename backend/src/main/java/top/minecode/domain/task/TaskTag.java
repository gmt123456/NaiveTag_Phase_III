package top.minecode.domain.task;

import java.util.LinkedList;
import java.util.List;

/**
 * Created on 2018/5/16.
 * Description:
 * @author iznauy
 */
public enum TaskTag {

    military,
    nature,
    sports,
    humanity,
    science,
    others,
    all;

    public static List<TaskTag> getAllTags() {
        List<TaskTag> taskTags = new LinkedList<>();
        taskTags.add(military);
        taskTags.add(nature);
        taskTags.add(sports);
        taskTags.add(humanity);
        taskTags.add(science);
        taskTags.add(others);
        return taskTags;
    }
}

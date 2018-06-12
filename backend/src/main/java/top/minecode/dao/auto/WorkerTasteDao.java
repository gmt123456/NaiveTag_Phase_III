package top.minecode.dao.auto;

import org.springframework.stereotype.Repository;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.domain.task.TaskTag;
import top.minecode.domain.task.TaskType;
import top.minecode.po.auto.WorkerTastePO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created on 2018/5/26.
 * Description:
 *
 * @author iznauy
 */
@Repository
public class WorkerTasteDao {

    private CommonOperation<WorkerTastePO> commonOperation
             = new CommonOperation<WorkerTastePO>(WorkerTastePO.class);

    public void addTastePO(WorkerTastePO workerTastePO) {
        commonOperation.add(workerTastePO);
    }

    public void updateTastePO(WorkerTastePO workerTastePO) {
        commonOperation.update(workerTastePO);
    }

    public List<WorkerTastePO> getAll() {
        return commonOperation.getAll();
    }

    public WorkerTastePO get(String email) {
        return commonOperation.getBySingleField("email", email);
    }

    public void addTypeClick(Set<TaskType> types, Set<TaskTag> tags, String email) {
        WorkerTastePO taste = get(email);
        Map<TaskType, Double> typeClickTimes = taste.getTypeClickTimes();
        Map<TaskTag, Double> tagClickTimes = taste.getTagClickTimes();

        double typeSize = types.size();
        for (TaskType type: types)
            typeClickTimes.put(type, typeClickTimes.get(type) + 1.0 / typeSize);

        double tagSize = tags.size();
        for (TaskTag tag: tags)
            tagClickTimes.put(tag, tagClickTimes.get(tag) + 1.0 / tagSize);

        commonOperation.update(taste);

    }
}

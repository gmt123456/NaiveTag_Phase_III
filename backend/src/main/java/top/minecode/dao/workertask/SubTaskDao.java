package top.minecode.dao.workertask;

import org.springframework.stereotype.Repository;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.domain.task.SubTask;
import top.minecode.domain.task.SubTaskParticipation;
import top.minecode.po.task.SubTaskPO;
import top.minecode.po.worker.SubTaskParticipationPO;

import java.util.List;

/**
 * Created on 2018/5/21.
 * Description:
 *
 * @author iznauy
 */
@Repository
public class SubTaskDao {

    private CommonOperation<SubTaskPO> subTaskHelper = new CommonOperation<SubTaskPO>(SubTaskPO.class.getName());

    private CommonOperation<SubTaskParticipationPO> subTaskParticipationHelper =
            new CommonOperation<SubTaskParticipationPO>(SubTaskParticipationPO.class.getName());

    public List<SubTaskPO> getSubTasksByIdList(List<Integer> ids) {
        return subTaskHelper.getValuesInSpecificSet(ids,"id");
    }

    public List<SubTaskParticipationPO> getSubTaskParticipationByIds(List<Integer> ids) {
        return subTaskParticipationHelper.getValuesInSpecificSet(ids, "id");
    }

}

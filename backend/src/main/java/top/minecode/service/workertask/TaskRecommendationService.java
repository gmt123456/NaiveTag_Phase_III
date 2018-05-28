package top.minecode.service.workertask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.minecode.dao.auto.WorkerTasteDao;
import top.minecode.domain.task.RankType;
import top.minecode.domain.task.Task;
import top.minecode.domain.task.TaskTag;
import top.minecode.domain.task.TaskType;
import top.minecode.po.auto.WorkerTastePO;

import java.util.List;
import java.util.Map;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
@Service
public class TaskRecommendationService {

    private WorkerTasteDao tasteDao;

    public WorkerTasteDao getTasteDao() {
        return tasteDao;
    }

    @Autowired
    public void setTasteDao(WorkerTasteDao tasteDao) {
        this.tasteDao = tasteDao;
    }

    private double[] getHistogram(WorkerTastePO tastePO) {
        Map<TaskTag, Double> tagDoubleMap = tastePO.getTagClickTimes();
        Map<TaskType, Double> typeDoubleMap = tastePO.getTypeClickTimes();
        double total = 0.0;
        int size1 = tagDoubleMap.size();
        int size2 = typeDoubleMap.size();
        double[] vector = new double[size1 + size2];
        return null;
    }

    private double calcHistogramIntersectionSimilar(WorkerTastePO tastePO1, WorkerTastePO tastePO2) {
        double[] his1 = getHistogram(tastePO1);
        double[] his2 = getHistogram(tastePO2);
        double result = 0;
        for(int i = 0; i < his1.length; i++)
            result = Math.min(his1[i], his2[i]);
        return result;
    }

    private void getTaskRecommendationsBySimilarUsers(String email) {
        List<WorkerTastePO> tastePOs = tasteDao.getAll();
        WorkerTastePO currentWorkerTaste = null;
        for (WorkerTastePO po: tastePOs)
            if (po.getEmail().equals(email)){
                currentWorkerTaste = po;
                break;
            }

        assert currentWorkerTaste != null;



    }


    public List<Task> getTaskRecommendations(String email) {
        return null;
    }

}

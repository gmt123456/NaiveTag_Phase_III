package top.minecode.service.staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.minecode.dao.staff.StaffDao;
import top.minecode.dao.staff.TaskCheckDao;
import top.minecode.dao.workertask.TaskParticipationDao;
import top.minecode.domain.tag.TagResult;
import top.minecode.domain.taskcheck.SubCheckTaskState;
import top.minecode.po.task.SubCheckTaskPO;
import top.minecode.po.worker.SubTaskParticipationPO;
import top.minecode.web.common.WebConfig;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created on 2018/6/2.
 * Description:
 *
 * @author iznauy
 */
@Service
public class StaffSubTaskCheckService {

    private TaskCheckDao checkDao;

    private StaffDao staffDao;

    private TaskParticipationDao participationDao;

    public TaskCheckDao getCheckDao() {
        return checkDao;
    }

    public StaffDao getStaffDao() {
        return staffDao;
    }

    public TaskParticipationDao getParticipationDao() {
        return participationDao;
    }

    @Autowired
    public void setParticipationDao(TaskParticipationDao participationDao) {
        this.participationDao = participationDao;
    }

    @Autowired
    public void setCheckDao(TaskCheckDao checkDao) {
        this.checkDao = checkDao;
    }

    @Autowired
    public void setStaffDao(StaffDao staffDao) {
        this.staffDao = staffDao;
    }

    // 获取没有mark的一张新图片
    public String morePic(int participationId) {
        SubCheckTaskPO subCheckTaskPO = checkDao.getSubCheckById(participationId);
        Set<String> markedPics = subCheckTaskPO.getPicAccept().keySet();
        Set<String> allPics = subCheckTaskPO.getSampledTags().keySet();
        if (allPics.size() == markedPics.size()) {
            commit(subCheckTaskPO);
            return null;
        }
        List<String> diff = allPics.stream().filter(markedPics::contains)
                .collect(Collectors.toList());
        Random random = new Random();
        int index = random.nextInt(diff.size());
        return diff.get(index);
    }

    public TagResult getLabelInfo(int participationId, String url) {
        SubCheckTaskPO subCheckTaskPO = checkDao.getSubCheckById(participationId);
        return WebConfig.getGson().fromJson(subCheckTaskPO.getSampledTags().get(url), TagResult.class);
    }

    public void mark(int participationId, String url, boolean accept) {
        SubCheckTaskPO subCheckTaskPO = checkDao.getSubCheckById(participationId);
        subCheckTaskPO.getPicAccept().put(url, accept);
        checkDao.updateSubCheck(subCheckTaskPO);
    }

    private void commit(SubCheckTaskPO subCheckTaskPO) {
        subCheckTaskPO.setCheckTaskState(SubCheckTaskState.finished);
        Map<String, Boolean> resultMap = subCheckTaskPO.getPicAccept();

        int acceptCount = 0;
        Collection<Boolean> values =  resultMap.values();
        for (Boolean value: values)
            if (value)
                acceptCount += 1;

        double errorRate = acceptCount * 1.0 / subCheckTaskPO.getAcceptRate();

        SubTaskParticipationPO subTaskParticipationPO;

    }
}

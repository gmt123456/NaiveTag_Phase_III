package top.minecode.service.workertask;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.minecode.dao.log.WorkerLogDao;
import top.minecode.dao.worker.WorkerInfoDao;
import top.minecode.dao.workertask.TaskSearchDao;
import top.minecode.domain.task.*;
import top.minecode.domain.user.worker.Division;
import top.minecode.domain.user.worker.Rank;
import top.minecode.po.log.WorkerSearchLogPO;
import top.minecode.po.task.TaskPO;
import top.minecode.po.worker.WorkerPO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 2018/5/23.
 * Description:
 *
 * @author iznauy
 */
@Service
public class WorkerTaskSearchService {

    private WorkerInfoDao workerInfoDao;

    private TaskSearchDao taskSearchDao;

    private WorkerLogDao logDao;

    public WorkerLogDao getLogDao() {
        return logDao;
    }

    @Autowired
    public void setLogDao(WorkerLogDao logDao) {
        this.logDao = logDao;
    }

    public WorkerInfoDao getWorkerInfoDao() {
        return workerInfoDao;
    }

    @Autowired
    public void setWorkerInfoDao(WorkerInfoDao workerInfoDao) {
        this.workerInfoDao = workerInfoDao;
    }

    public TaskSearchDao getTaskSearchDao() {
        return taskSearchDao;
    }

    @Autowired
    public void setTaskSearchDao(TaskSearchDao taskSearchDao) {
        this.taskSearchDao = taskSearchDao;
    }

    private void recordSearchLog(String email, TaskType taskType, TaskTag taskTag, String key, RankType rankType) {
        WorkerSearchLogPO searchLogPO = new WorkerSearchLogPO(key, taskType, taskTag, new Date(), email, rankType);
        logDao.addSearchLog(searchLogPO);
    }

    public List<Task> searchTask(String email, TaskType taskType,
                                 TaskTag taskTag,
                                 RankType rankType, int begin, int step,
                                 String key, boolean canAccept) {

        recordSearchLog(email, taskType, taskTag, key, rankType); // record log

        List<TaskPO> rawPOs = taskSearchDao.searchTasks(key).stream().filter(e -> e.getTaskState() == TaskState.ON_GOING)
                .collect(Collectors.toList());

        if (taskType != null) { // 根据任务类型筛选任务
            rawPOs = rawPOs.stream().filter(e -> e.getSpecificTasks().keySet().contains(taskType))
                    .collect(Collectors.toList());
        }

        if (taskTag != TaskTag.all) { // 根据任务标签来筛选任务
            rawPOs = rawPOs.stream().filter(e -> e.getTaskTags().contains(taskTag))
                    .collect(Collectors.toList());
        }

        if (canAccept) { // 根据段位来筛选任务
            WorkerPO worker = workerInfoDao.getWorkerPOByEmail(email);
            Division workerDivision = Division.convert(worker.getScore()); // 获取用户段位信息
            rawPOs = rawPOs.stream().filter(e -> Division
                    .difference(workerDivision, e.getLowestDivision()) >= 0)
                    .collect(Collectors.toList());
        }

        List<TaskPO> sortedTaskPOs = sortTasks(rawPOs, rankType, key); // 进行黑心排序

        List<Task> results = new ArrayList<>();

        for (int i = begin; i < begin + step && i < sortedTaskPOs.size(); i++) {
            results.add(Task.fromPO(sortedTaskPOs.get(i)));
        }

        return results;
    }

    private List<TaskPO> sortTasks(List<TaskPO> tasks, RankType rankType, String key) {
        List<TaskContainer> containers = tasks.stream().map(e -> new TaskContainer(e, rankType, key))
                .collect(Collectors.toList());
        Collections.sort(containers);
        return containers.stream().map(TaskContainer::getTaskPO).collect(Collectors.toList());
    }

    private static class TaskContainer implements Comparable<TaskContainer> {

        private double value;

        private TaskPO taskPO;

        TaskContainer(TaskPO taskPO, RankType rankType, String key) {
            this.taskPO = taskPO;

            double ratio = 1; // 任务和搜索的的紧密程度
            String taskName = taskPO.getTaskName();
            String taskDescription = taskPO.getTaskDescription();
            if (taskName.contains(key)) {
                ratio *= 100;
            } else if (taskDescription.contains(key)) {
                ratio *= 10;
            }

            // 计算任务的排序值
            if (rankType == null || rankType == RankType.DEFAULT) {
                value = ratio * taskPO.getAdRate();
            } else if (rankType == RankType.MONEY_DESCEND) {
                value = taskPO.getTotalDollars() * ratio;
            } else { // RankType.MONEY_ASCEND
                value = - taskPO.getTotalDollars() / ratio;
            }
        }

        @Override
        public int compareTo(@NotNull WorkerTaskSearchService.TaskContainer o) {
            return Double.compare(o.value, value);
        }

        public TaskPO getTaskPO() {
            return taskPO;
        }
    }


}

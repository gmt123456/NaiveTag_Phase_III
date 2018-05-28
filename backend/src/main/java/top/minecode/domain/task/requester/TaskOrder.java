package top.minecode.domain.task.requester;

import top.minecode.domain.task.TaskType;
import top.minecode.po.task.SpecificTaskPO;
import top.minecode.po.task.TaskPO;
import top.minecode.service.util.CacheItem;
import top.minecode.web.requester.task.NewTaskCommand.NewSpecificTask;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 2018/5/27.
 * Description:
 * @author Liao
 */
public class TaskOrder extends CacheItem {

    private static long ORDER_EXPIRE_TIME = 5;

    private TaskPO taskPO;
    private List<NewSpecificTask> specificTasks;
    private String zipFilePath;
    private double payLowerBound;
    private String email;

    public TaskOrder(TaskPO taskPO, String zipFilePath, double payLowerBound,
                     String email, List<NewSpecificTask> specificTasks) {
        super(ORDER_EXPIRE_TIME);
        this.taskPO = taskPO;
        this.zipFilePath = zipFilePath;
        this.payLowerBound = payLowerBound;
        this.email = email;
        this.specificTasks = specificTasks;
    }

    public List<NewSpecificTask> getSpecificTasks() {
        return specificTasks;
    }

    public String getEmail() {
        return email;
    }

    public TaskPO getTaskPO() {
        return taskPO;
    }

    public String getZipFilePath() {
        return zipFilePath;
    }

    public double getPayLowerBound() {
        return payLowerBound;
    }

    public List<SpecificTaskPO> getSpecificPOS(double dollars) {
        taskPO.setTotalDollars(dollars);

        // Use BigDecimal to calculate each part's dollars
        BigDecimal totalDollars = new BigDecimal(dollars);
        BigDecimal total = new BigDecimal(specificTasks.stream().mapToInt(NewSpecificTask::getType).sum());
        return specificTasks.stream()
                .map(e -> {
                    BigDecimal part = new BigDecimal(e.getType());
                    double itemDollars = part.divide(total, 3, RoundingMode.HALF_UP)
                            .multiply(totalDollars).doubleValue();
                    return new SpecificTaskPO(TaskType.convert(e.getType()),
                        e.getDescription(), e.getLabels(), itemDollars);
                }).collect(Collectors.toList());
    }

    public void setAdRate(double adRate) {
        taskPO.setAdRate(adRate);
    }

    @Override
    public String toString() {
        return "TaskOrder{" +
                ", zipFilePath='" + zipFilePath + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

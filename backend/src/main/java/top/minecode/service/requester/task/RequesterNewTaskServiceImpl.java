package top.minecode.service.requester.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.minecode.dao.requester.info.RequesterInfoDao;
import top.minecode.dao.requester.task.RequesterTaskDao;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.dao.utils.ZipHelper;
import top.minecode.domain.task.TaskState;
import top.minecode.domain.task.requester.TaskCreationOptions;
import top.minecode.domain.task.requester.TaskOrder;
import top.minecode.domain.utils.ResultMessage;
import top.minecode.po.auto.TaskVectorPO;
import top.minecode.po.log.ReleaseTaskLogPO;
import top.minecode.po.log.RequesterAccountLogPO;
import top.minecode.po.task.SpecificTaskPO;
import top.minecode.po.task.TaskPO;
import top.minecode.service.util.ImageUtils;
import top.minecode.service.util.PathUtil;
import top.minecode.service.util.RandomUtil;
import top.minecode.web.requester.task.NewTaskCommand;
import top.minecode.web.requester.task.PayCommand;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.zip.ZipFile;

/**
 * Created on 2018/5/26.
 * Description:
 * @author Liao
 */
@Service("requesterNewTaskServiceImpl")
public class RequesterNewTaskServiceImpl implements RequesterNewTaskService {

    private static Logger log = LoggerFactory.getLogger(RequesterNewTaskServiceImpl.class);

    private TaskCreationOptions options = new TaskCreationOptions();
    private TaskOrderCache orderCache;
    private RequesterTaskDao taskDao;
    private RequesterInfoDao infoDao;

    @Autowired
    public void setInfoDao(RequesterInfoDao infoDao) {
        this.infoDao = infoDao;
    }

    @Autowired
    public void setTaskDao(RequesterTaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Autowired
    public void setOrderCache(TaskOrderCache orderCache) {
        this.orderCache = orderCache;
    }

    @Override
    public TaskCreationOptions getCreationInfo() {
        return options;
    }

    @Override
    public ResultMessage createTask(NewTaskCommand newTaskCommand, String email) {
        TaskPO taskPO = new TaskPO();

        setBasicInfo(taskPO, newTaskCommand, email);

        // Set cover and background
        try {
            // Transfer cover
            Optional.ofNullable(processImage(newTaskCommand.getCover(),
                    ImageUtils::transferTaskCover, ImageUtils::getRandomTaskCover))
                    .ifPresent(taskPO::setCover);

            // Transfer background
            Optional.ofNullable(processImage(newTaskCommand.getBackgroundImage(),
                    ImageUtils::transferBackground, ImageUtils::getRandomTaskBackground))
                    .ifPresent(taskPO::setBackgroundImage);

            // Save dataset to file system
            // Create directory for saving data set
            MultipartFile dataset = newTaskCommand.getDataset();
            String randomFileName = RandomUtil.getRandomFileName(dataset.getOriginalFilename());
            String path = PathUtil.getBasePath() + PathUtil.getRequesterDataRec(email) + randomFileName;
            File targetFile = new File(path);
            if (!targetFile.getParentFile().exists() && !targetFile.getParentFile().mkdirs()) {
                log.error("Create dataset's parent's directory failed");
                return ResultMessage.failure("Something is wrong");
            }

            // Set result file's path

            taskPO.setResultFilePath(PathUtil.getRequesterDataRec(email) +
                    getNameWithoutSuffix(randomFileName) + ".json");

            // Write dataset to file and count picture's number of the dataset
            dataset.transferTo(targetFile);
            ZipFile zipFile = new ZipFile(targetFile);
            int pictureNum = ZipHelper.countEntriesInZipFile(zipFile);

            taskPO.setPicNum(pictureNum);

            // Calculate lower bound of payment
            double paymentLowerBound = newTaskCommand.getTasks().stream()
                    .mapToDouble(e -> e.getPrizeLowerBound(pictureNum)).sum();

            // Create order and add it to the cache
            TaskOrder taskOrder = new TaskOrder(taskPO, path, paymentLowerBound, email, newTaskCommand.getTasks());
            String orderId = orderCache.addTaskOrder(taskOrder);

            return ResultMessage.payMessage(orderId, paymentLowerBound, pictureNum);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultMessage.failure(e.getMessage());
        }
    }

    @Override
    public ResultMessage pay(PayCommand payCommand) {
        TaskOrder order = orderCache.getOrder(payCommand.getOrderId());

        if (order == null)
            return ResultMessage.failure("Order is expired");
        else if (!order.getEmail().equals(payCommand.getUserEmail())) {
            log.error("Email not match");
            return ResultMessage.failure("Something is wrong");
        }

        // Minus money
        ResultMessage dollarsRes = infoDao.updateAccount(payCommand.getUserEmail(), -payCommand.getDollars(),
                RequesterAccountLogPO.ChangeType.RELEASE_TASK);
        if (dollarsRes.failed())
            return dollarsRes;

        dollarsRes = infoDao.updateAccount(payCommand.getUserEmail(), -payCommand.getAdvertisementDollars(),
                RequesterAccountLogPO.ChangeType.ADVERTISEMENT);
        if (dollarsRes.failed())
            return dollarsRes;

        // Add dollars and advertisement dollars to taskPO
        order.setAdRate(payCommand.getAdvertisementDollars() / order.getPayLowerBound());
        order.getTaskPO().setPrizeRate(payCommand.getDollars() / order.getPayLowerBound());
        List<SpecificTaskPO> specificTaskPOS = order.getSpecificPOS(payCommand.getDollars());

        // Unzip the dataset
        File zipFile = new File(order.getZipFilePath());
        String destPath = new File(zipFile.getParent(), getNameWithoutSuffix(zipFile.getName())).getPath();

        try {
            ZipHelper.unZipDataSet(order.getZipFilePath(), destPath);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultMessage.failure("Paying failed");
        }

        TaskPO taskPO = order.getTaskPO();
        taskPO.setBeginDate(new Date());

        // Add task information to database
        if (!taskDao.addTask(taskPO, specificTaskPOS, destPath))
            return ResultMessage.failure("Pay failed");

        // Add release tak log
        ReleaseTaskLogPO logPO = new ReleaseTaskLogPO(taskPO.getId(), taskPO.getBeginDate());
        CommonOperation.template(session -> {
            session.persist(logPO);
            session.flush();
        });

        // Insert task vector
        TaskVectorPO vectorPO = TaskVectorPO.fromTaskPO(taskPO);
        CommonOperation.template(session -> {
            session.persist(vectorPO);
            session.flush();
        });

        return ResultMessage.success();
    }

    private Date transformLocalDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Process image by function process, if image is not
     * null, which means the user has defined a image, the
     * process function will process it, otherwise will return
     * alternate by supplier
     */
    private String processImage(String image, FunctionWithException<String,
            String> process, Supplier<String> alternate) throws IOException {
        if (image == null || image.isEmpty())
            return alternate.get();

        return process.apply(image);
    }

    private String getNameWithoutSuffix(String name) {
        int index = name.lastIndexOf('.');
        if (index == -1)
            return name;

        return name.substring(0, index);
    }

    // Set basic information for task po
    private void setBasicInfo(TaskPO taskPO, NewTaskCommand newTaskCommand, String email) {
        taskPO.setEndDate(transformLocalDate(newTaskCommand.getDeadline()));
        taskPO.setLowestDivision(newTaskCommand.getLowestDivision());
        taskPO.setTaskTags(newTaskCommand.getTags());
        taskPO.setTaskState(TaskState.ON_GOING);
        taskPO.setTaskName(newTaskCommand.getTitle());
        taskPO.setTaskDescription(newTaskCommand.getDescription());
        taskPO.setOwnerEmail(email);
        taskPO.setReadme(newTaskCommand.getReadme());
    }

    @FunctionalInterface
    private interface FunctionWithException<S, T> {
        T apply(S s) throws IOException;
    }
}

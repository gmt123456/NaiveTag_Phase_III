package top.minecode.service.requester.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.minecode.domain.task.requester.TaskOrder;
import top.minecode.service.util.CacheItem;
import top.minecode.service.util.Encryptor;
import top.minecode.service.util.SimpleCache;

import java.io.File;
import java.util.List;
import java.util.Random;

/**
 * Created on 2018/5/27.
 * Description:
 * @author Liao
 */
@Component("simpleOrderCache")
public class SimpleTaskOrderCache implements TaskOrderCache {

    private static String SALT = "1926";

    private static Logger log = LoggerFactory.getLogger(SimpleTaskOrderCache.class);
    private static SimpleCache<TaskOrder> orderCache = new SimpleCache<>();

    private Encryptor encryptor;

    @Autowired
    public void setEncryptor(Encryptor encryptor) {
        this.encryptor = encryptor;
    }

    @Override
    public TaskOrder getOrder(String orderId) {
        return orderCache.get(orderId);
    }

    @Override
    public String addTaskOrder(TaskOrder order) {
        Random random = new Random();
        return orderCache.add(order, o -> encryptor.encrypt(order.getEmail(), SALT + random.nextInt()));
    }

    /**
     * Refresh the <tt>SimpleCache</tt> per minutes
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    private void refresh() {
        List<CacheItem> expiredOrders = orderCache.refresh();
        // Delete the zip file
        for (CacheItem item : expiredOrders) {
            TaskOrder order = (TaskOrder) item;
            File zipFile = new File(order.getZipFilePath());
            if (!zipFile.delete()) {
                log.error("Delete expired order's dataset failed");
            }
        }

        log.info("Task orders refresh");
        log.info("Expired orders list: " + expiredOrders.toString());
    }
}

package top.minecode.web.workertask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import top.minecode.service.workertask.TaskSettlementService;

import java.io.IOException;

/**
 * Created on 2018/6/4.
 * Description: 任务结算
 *
 * @author iznauy
 */
@Controller
public class TaskSettlementController {

    private TaskSettlementService settlementService;

    public TaskSettlementService getSettlementService() {
        return settlementService;
    }

    @Autowired
    public void setSettlementService(TaskSettlementService settlementService) {
        this.settlementService = settlementService;
    }

    @Scheduled(cron = "0 0 * * * ?")
    public void settleTasks() throws IOException {
        settlementService.settleTasks();
    }

}

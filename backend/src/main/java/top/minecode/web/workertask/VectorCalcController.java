package top.minecode.web.workertask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import top.minecode.service.ml.WorkerVectorCalculateService;

/**
 * Created on 2018/6/25.
 * Description:
 *
 * @author iznauy
 */
@Controller
public class VectorCalcController {

    private WorkerVectorCalculateService calculateService;

    public WorkerVectorCalculateService getCalculateService() {
        return calculateService;
    }

    @Autowired
    public void setCalculateService(WorkerVectorCalculateService calculateService) {
        this.calculateService = calculateService;
    }

    @Scheduled(cron = "0/30 * * * * ?")
    public void calc() {
        calculateService.calculateWorkerVectors();
    }

}

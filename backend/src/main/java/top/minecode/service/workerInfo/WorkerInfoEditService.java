package top.minecode.service.workerInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.minecode.dao.worker.WorkerInfoDao;
import top.minecode.domain.user.worker.WorkerInfoEditResponse;
import top.minecode.po.worker.WorkerPO;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
@Service
public class WorkerInfoEditService {

    private WorkerInfoDao workerInfoDao;

    public WorkerInfoDao getWorkerInfoDao() {
        return workerInfoDao;
    }

    @Autowired
    public void setWorkerInfoDao(WorkerInfoDao workerInfoDao) {
        this.workerInfoDao = workerInfoDao;
    }

    public WorkerInfoEditResponse editBasicInfo(String email, String userName) {
        WorkerPO workerPO = workerInfoDao.getWorkerPOByEmail(email);
        workerPO.setName(userName);
        String response = WorkerInfoEditResponse.SUCCESS;
        if (!workerInfoDao.updateWorkPO(workerPO))
            response = WorkerInfoEditResponse.FAILURE;
        return new WorkerInfoEditResponse(response);
    }

    private String saveAvatar(MultipartFile avatar) { // TODO: 不太懂文件保存
        return null;
    }

    public WorkerInfoEditResponse editAvatar(String email, MultipartFile file) {
        WorkerPO workerPO = workerInfoDao.getWorkerPOByEmail(email);
        workerPO.setAvatar(saveAvatar(file));
        String response = WorkerInfoEditResponse.SUCCESS;
        if (!workerInfoDao.updateWorkPO(workerPO))
            response = WorkerInfoEditResponse.FAILURE;
        return new WorkerInfoEditResponse(response);
    }

    public WorkerInfoEditResponse editPassword(String email, String rawPassword, String newPassword) {
        WorkerPO workerPO = workerInfoDao.getWorkerPOByEmail(email);
        if (!workerPO.getPassword().equals(rawPassword))
            return new WorkerInfoEditResponse(WorkerInfoEditResponse.INVALID_PASSWORD);
        workerPO.setPassword(newPassword);
        String response = WorkerInfoEditResponse.SUCCESS;
        if (!workerInfoDao.updateWorkPO(workerPO))
            response = WorkerInfoEditResponse.FAILURE;
        return new WorkerInfoEditResponse(response);
    }

}

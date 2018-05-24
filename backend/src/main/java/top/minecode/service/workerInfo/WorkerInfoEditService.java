package top.minecode.service.workerInfo;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import top.minecode.dao.worker.RankDao;
import top.minecode.dao.worker.WorkerInfoDao;
import top.minecode.domain.user.worker.WorkerInfoEditResponse;
import top.minecode.po.worker.RankPO;
import top.minecode.po.worker.WorkerPO;
import top.minecode.service.util.Encryptor;
import top.minecode.service.util.PathUtil;
import top.minecode.service.util.RandomUtil;

import java.io.File;
import java.io.IOException;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
@Service
public class WorkerInfoEditService {

    private WorkerInfoDao workerInfoDao;
    private Encryptor encryptor;

    private RankDao rankDao;

    public RankDao getRankDao() {
        return rankDao;
    }

    @Autowired
    public void setRankDao(RankDao rankDao) {
        this.rankDao = rankDao;
    }

    public Encryptor getEncryptor() {
        return encryptor;
    }

    @Autowired
    public void setEncryptor(Encryptor encryptor) {
        this.encryptor = encryptor;
    }

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

        RankPO rankPO = rankDao.getRankPOByEmail(email);
        rankPO.setUserName(userName);
        rankDao.updateRank(rankPO);

        return new WorkerInfoEditResponse(response);
    }

//    private String saveAvatar(String run avatar, String email) { // TODO: 不太懂文件保存
//        email = email.replace('@', '_').replace('.', '_');
//        return null;
//    }

    public WorkerInfoEditResponse editAvatar(String email, String fileData, String suffix) {
        WorkerPO workerPO = workerInfoDao.getWorkerPOByEmail(email);

        // 构造一个文件
        byte[] bsPic = Base64Utils.decodeFromString(fileData);

        String randomName = "avatar/" + RandomUtil.getRandomFileName() + suffix;

        String fileName = PathUtil.getBasePath() + randomName;

        File imageFile = new File(fileName);
        try {
            FileUtils.writeByteArrayToFile(imageFile, bsPic);
        } catch (IOException e) {
            e.printStackTrace();
            return new WorkerInfoEditResponse(WorkerInfoEditResponse.FAILURE);
        }

        workerPO.setAvatar(randomName);
        String response = WorkerInfoEditResponse.SUCCESS;
        if (!workerInfoDao.updateWorkPO(workerPO))
            response = WorkerInfoEditResponse.FAILURE;

        // 更新 rank表中的头像信息
        RankPO rankPO = rankDao.getRankPOByEmail(email);
        rankPO.setAvatar(randomName);
        rankDao.updateRank(rankPO);

        return new WorkerInfoEditResponse(response);
    }

    public WorkerInfoEditResponse editPassword(String email, String rawPassword, String newPassword) {
        WorkerPO workerPO = workerInfoDao.getWorkerPOByEmail(email);
        String encryptedPwd = encryptor.encrypt(rawPassword, email);
        if (!workerPO.getPassword().equals(encryptedPwd))
            return new WorkerInfoEditResponse(WorkerInfoEditResponse.INVALID_PASSWORD);
        workerPO.setPassword(encryptor.encrypt(newPassword, email));
        String response = WorkerInfoEditResponse.SUCCESS;
        if (!workerInfoDao.updateWorkPO(workerPO))
            response = WorkerInfoEditResponse.FAILURE;
        return new WorkerInfoEditResponse(response);
    }

}

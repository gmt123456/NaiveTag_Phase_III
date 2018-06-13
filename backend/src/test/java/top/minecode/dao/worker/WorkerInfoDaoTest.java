package top.minecode.dao.worker;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:naive*")
public class WorkerInfoDaoTest {

    private WorkerInfoDao workerInfoDao;

    @Autowired
    public void setWorkerInfoDao(WorkerInfoDao workerInfoDao) {
        this.workerInfoDao = workerInfoDao;
    }

    @Test
    public void testGetCommitmentLog() {
        System.out.println(workerInfoDao.getWorkerCommitment("frog@worker.com"));
    }

    @Test
    public void testGetRankRate() {
        System.out.println(workerInfoDao.getWorkerSpeedRankRate("frog@worker.com"));
    }
}
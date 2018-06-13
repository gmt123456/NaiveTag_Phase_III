package top.minecode.dao.worker;

import org.hibernate.Query;
import org.springframework.stereotype.Service;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.domain.task.Task;
import top.minecode.domain.user.worker.WorkerCommitmentLog;
import top.minecode.po.log.JoinTaskLogPO;
import top.minecode.po.worker.FinishedTaskParticipationPO;
import top.minecode.po.worker.OnGoingTaskParticipationPO;
import top.minecode.po.worker.WorkerPO;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
@Service
public class WorkerInfoDao {

    private CommonOperation<WorkerPO> workerPOHelper = new CommonOperation<>(WorkerPO.class.getName());

    private CommonOperation<FinishedTaskParticipationPO> finishParticipationHelper =
            new CommonOperation<>(FinishedTaskParticipationPO.class.getName());

    private CommonOperation<OnGoingTaskParticipationPO> onGoingTaskParticipationHelper =
            new CommonOperation<>(OnGoingTaskParticipationPO.class.getName());


    public WorkerPO getWorkerPOByEmail(String email) {
        return workerPOHelper.getBySingleField("email", email);
    }


    public boolean addWorker(WorkerPO worker) {
        return workerPOHelper.add(worker);
    }

    public boolean updateWorkPO(WorkerPO workerPO) {
        return workerPOHelper.update(workerPO);
    }

    public List<FinishedTaskParticipationPO> getFinishedTasks(String email) {
        WorkerPO worker = getWorkerPOByEmail(email);
        List<Integer> finishedParticipation = worker.getFinishedTaskParticipation();
        return finishParticipationHelper.getValuesInSpecificSet(finishedParticipation, "id");
    }

    public List<OnGoingTaskParticipationPO> getOnGoingTasks(String email) {
        WorkerPO worker = getWorkerPOByEmail(email);
        List<Integer> onGoingParticipation = worker.getOnGoingTaskParticipation();
        return onGoingTaskParticipationHelper.getValuesInSpecificSet(onGoingParticipation, "id");
    }

    public List<Integer> getParticipatedTasks(String email) {
        WorkerPO worker = getWorkerPOByEmail(email);
        List<Integer> finishedParticipation = worker.getFinishedTaskParticipation();
        List<Integer> onGoingParticipation = worker.getOnGoingTaskParticipation();
        finishedParticipation.addAll(onGoingParticipation);

        return finishedParticipation;
    }

    public boolean persistWorker(WorkerPO worker) {
        return workerPOHelper.update(worker);
    }

    public List<WorkerPO> getAll() {
        return workerPOHelper.getAll();
    }

    public List<WorkerPO> getListByEmails(List<String> emails) {
        return workerPOHelper.getValuesInSpecificSet(emails, "email");
    }

    public List<WorkerCommitmentLog> getWorkerCommitment(String email) {
        String hql = "select new top.minecode.domain.user.worker.WorkerCommitmentLog( " +
                " cast(count(t) as int), max(t.commitTime), t.taskId, p.joinTime) " +
                " from TaskCommitmentLogPO t, JoinTaskLogPO p where t.userEmail=:mail " +
                " and p.userEmail = p.userEmail and p.taskId=t.taskId group by t.taskId, p.joinTime";

        return CommonOperation.template(session -> {
            Query query = session.createQuery(hql);
            query.setParameter("mail", email);
            //noinspection unchecked
            return (List<WorkerCommitmentLog>) query.list();
        });
    }

    /**
     *
     * @param email worker's email
     * @return worker's speed: commitment times per day
     * @throws ArithmeticException if any worker's speed is Nan
     */
    private double getWorkerCommitmentSpeed(String email) {
        List<WorkerCommitmentLog> commitmentLogs = getWorkerCommitment(email);
        return commitmentLogs.stream().mapToDouble(WorkerCommitmentLog::getSpeed)
                .average().orElseThrow(ArithmeticException::new);
    }

    public double getWorkerSpeedRankRate(String email) {
        String hql = "select t.email from WorkerPO t where t.email<>?";
        List<String> emails = CommonOperation.executeSQL(String.class, hql, email);

        List<Double> otherSpeeds = emails.stream().map(this::getWorkerCommitmentSpeed).sorted().collect(Collectors.toList());
        double thisSpeed = getWorkerCommitmentSpeed(email);
        for (int i = 0; i < otherSpeeds.size(); i++) {
            if (thisSpeed < otherSpeeds.get(i))
                return (i + 1) * 1. / (otherSpeeds.size() + 1);
        }

        return 1. / (otherSpeeds.size() + 1);  // Means the first
    }

    public void addJoinTaskLog(String email, int taskId) {
        JoinTaskLogPO logPO = new JoinTaskLogPO(email, taskId, new Date());
        CommonOperation.template(session -> {
            session.persist(logPO);
            session.flush();
        });
    }
}

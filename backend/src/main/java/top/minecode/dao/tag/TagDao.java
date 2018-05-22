package top.minecode.dao.tag;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.dao.utils.DaoConfig;
import top.minecode.dao.utils.HibernateUtils;
import top.minecode.domain.tag.TagResult;
import top.minecode.domain.task.SubTaskParticipation;
import top.minecode.domain.task.TaskType;
import top.minecode.po.worker.SubTaskParticipationPO;

import java.util.Map;

/**
 * Created on 2018/5/22.
 * Description:
 *
 * @author iznauy
 */
@Repository
public class TagDao {

    private CommonOperation<SubTaskParticipationPO> subTaskParticipationHelper =
            new CommonOperation<>(SubTaskParticipationPO.class.getName());

    private SubTaskParticipationPO getParticipationByUserEmailTaskIdAndSubTaskId(String userEmail,
                                                    int taskId, int subTaskId) {
        String hql = "select t from " + SubTaskParticipationPO.class + " t where t.email = :email and" +
                "t.taskId = :taskId and t.subTaskId = :subTaskId";
        Session session = HibernateUtils.getCurrentSession();
        session.getTransaction().begin();
        SubTaskParticipationPO po = (SubTaskParticipationPO) session.createQuery(hql).setParameter("email", userEmail)
                    .setParameter("taskId", taskId)
                    .setParameter("subTaskId", subTaskId).uniqueResult();
        session.getTransaction().commit();
        HibernateUtils.closeSession();
        return po;
    }

    public void saveTagResult(String userEmail, int taskId, int subTaskId,
                              String url, TagResult tagResult) {
        SubTaskParticipationPO participationPO = getParticipationByUserEmailTaskIdAndSubTaskId(
                userEmail, taskId, subTaskId
        );
        if (participationPO != null) {
            participationPO.getTags().put(url, DaoConfig.getGson().toJson(tagResult, TagResult.class));
            subTaskParticipationHelper.update(participationPO);
        }
    }

    public TagResult getTagResult(String userEmail, int taskId, int subTaskId,
                                  String url) {
        SubTaskParticipationPO participationPO = getParticipationByUserEmailTaskIdAndSubTaskId(
                userEmail, taskId, subTaskId
        );
        if (participationPO != null) {
            Map<String, String> url2Tag = participationPO.getTags();
            if (!url2Tag.keySet().contains(url))
                return null;
            else {
                return DaoConfig.getGson().fromJson(url2Tag.get(url), TagResult.class);
            }
        }
        return null;
    }

}

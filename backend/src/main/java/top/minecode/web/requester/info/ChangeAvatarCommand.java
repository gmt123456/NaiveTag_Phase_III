package top.minecode.web.requester.info;

import top.minecode.dao.utils.CommonOperation;
import top.minecode.po.requester.RequesterPO;
import top.minecode.po.worker.RankPO;
import top.minecode.service.util.ImageUtils;

/**
 * Created on 2018/5/26.
 * Description:
 * @author Liao
 */
public class ChangeAvatarCommand implements ChangeCommand<RequesterPO> {

    private String avatar;

    @Override
    public void change(RequesterPO po) throws Exception {
        String avatarPath = ImageUtils.transferAvatar(avatar);
        po.setAvatar(avatarPath);

        // Change RankPO's avatar
        String hql = "select t from RankPO t where t.userEmail=:mail";
        boolean updateRankSucceed = CommonOperation.template(session -> {
            RankPO rankPO = (RankPO) session.createQuery(hql).
                    setParameter("mail", po.getEmail()).iterate().next();
            rankPO.setAvatar(avatarPath);
            session.update(rankPO);
            session.flush();
        });

        if (!updateRankSucceed) {
            throw new IllegalStateException("Update failed");
        }
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}

package top.minecode.dao.worker;

import org.springframework.stereotype.Repository;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.po.worker.RankPO;

import java.util.List;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
@Repository
public class RankDao {

    private CommonOperation<RankPO> rankHelper = new CommonOperation<>(RankPO.class.getName());

    public List<RankPO> getRanks() {
        String sql = "select t from " + RankPO.class.getName() + " t where t.rank < 20 order by t.rank asc";
        return rankHelper.executeSQL(sql);
    }

    public int getRankByEmail(String email) {
        return rankHelper.getBySingleField("userEmail", email).getRank();
    }

    public RankPO getRankPOByEmail(String email) {
        return rankHelper.getBySingleField("userEmail", email);
    }

    public void updateRank(RankPO rankPO) {
        rankHelper.update(rankPO);
    }

    public void addRank(RankPO rankPO) {
        rankHelper.add(rankPO);
    }

}

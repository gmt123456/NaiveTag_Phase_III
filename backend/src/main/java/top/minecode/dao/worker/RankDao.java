package top.minecode.dao.worker;

import org.springframework.stereotype.Repository;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.domain.user.worker.Rank;
import top.minecode.po.worker.RankPO;
import top.minecode.po.worker.WorkerPO;

import java.util.Collections;
import java.util.Comparator;
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

    public void batchUpdate(List<RankPO> rankPOS) {
        rankHelper.batchUpdate(rankPOS);
    }

    public List<RankPO> getAll() {
        return rankHelper.getAll();
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

    public synchronized void addRank(RankPO rankPO) {
        rankPO.setRank(rankHelper.getAll().size() + 1);
        rankHelper.add(rankPO);
    }

    public double getScoreRankRate(String email) {
        RankPO rank = getRankPOByEmail(email);
        int size = rankHelper.getAll().size();
        return (size - rank.getRank()) * 1. / size;
    }
}

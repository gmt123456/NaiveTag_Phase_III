package top.minecode.domain.user.worker;

/**
 * Created on 2018/5/16.
 * Description: 段位
 *
 * @author iznauy
 */
public enum Division {

    Novice,
    Contributor,
    Expert,
    Master,
    GrandMaster;

    public static Division convert(double score) {
        if (score < 1000) {
            return Novice;
        } else if (score < 1500) {
            return Contributor;
        } else if (score < 2000) {
            return Expert;
        } else if (score < 3000) {
            return Master;
        } else {
            return GrandMaster;
        }
    }

}

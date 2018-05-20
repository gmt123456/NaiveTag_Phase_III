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

    private static int toInt(Division division) {
        switch (division) {
            case Expert:
                return 2000;
            case Master:
                return 3000;
            case Novice:
                return 1000;
            case Contributor:
                return 1500;
            case GrandMaster:
                return 6000;
            default:
                return 0;
        }
    }

    public static int difference(Division division1, Division division2) {
        return Division.toInt(division1) - Division.toInt(division2);
    }

}

package top.minecode.domain.task;

/**
 * Created on 2018/5/16.
 * Description:
 *
 * @author iznauy
 */
public enum SubTaskParticipationState {

    DOING,
    EXPIRED,
    FINISHED;

    public static SubTaskParticipationState convert(int subTaskState) {
        switch (subTaskState) {
            case 0:
                return DOING;
            case 1:
                return EXPIRED;
            case 2:
                return FINISHED;
            default:
                return null;
        }
    }

}

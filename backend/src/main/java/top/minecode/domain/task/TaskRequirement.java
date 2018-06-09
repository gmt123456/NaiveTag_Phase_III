package top.minecode.domain.task;

/**
 * Created on 2018/6/2.
 * Description:
 *
 * @author iznauy
 */
public enum TaskRequirement {

    COMMON(0.5, 0.5),
    SPEED(0.8, 0.2),
    QUALITY(0.2, 0.8);

    private static final double AD_RATE_FACTOR = 1.0;

    private double efficiency;
    private double quality;

    TaskRequirement(double efficiency, double quality) {
        this.efficiency = efficiency;
        this.quality = quality;
    }

    public double adRateToEfficiency(double adRate) {
        return this.efficiency * adRate * AD_RATE_FACTOR;
    }

    public double adRateToQuality(double adRate) {
        return this.quality * adRate * AD_RATE_FACTOR;
    }
}

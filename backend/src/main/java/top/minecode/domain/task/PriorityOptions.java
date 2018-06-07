package top.minecode.domain.task;

/**
 * Created on 2018/6/6.
 * Description:
 * @author Liao
 */
public enum PriorityOptions {
    SPEED(0.8, 0.2),
    QUALITY(0, 1.0),
    NORMAL(0.2, 0.8);

    private double speedPriorityValue;
    private double qualityPriorityValue;

    PriorityOptions(double speedPriorityValue, double qualityPriorityValue) {
        this.speedPriorityValue = speedPriorityValue;
        this.qualityPriorityValue = qualityPriorityValue;
    }

    public double getAdvertisementAdditionForSpeed(double adRate) {
        return this.speedPriorityValue * adRate;
    }

    public double getAdvertisementAdditionForQuality(double adRate) {
        return this.qualityPriorityValue * adRate;
    }

    public double getQualityPriorityValue() {
        return qualityPriorityValue;
    }

    public double getSpeedPriorityValue() {
        return speedPriorityValue;
    }
}

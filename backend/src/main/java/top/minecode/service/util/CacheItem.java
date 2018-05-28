package top.minecode.service.util;

import java.time.LocalDateTime;

/**
 * Created on 2018/5/27.
 * Description:
 * @author Liao
 */
public abstract class CacheItem {

    private LocalDateTime expirationTime;
    private long minutesEachUpdate;

    protected CacheItem(LocalDateTime expirationTime, long minutesEachUpdate) {
        this.expirationTime = expirationTime;
        this.minutesEachUpdate = minutesEachUpdate;
    }

    protected CacheItem(long minutesEachUpdate) {
        this.minutesEachUpdate = minutesEachUpdate;
        updateTime();
    }

    public boolean isActive() {
        return LocalDateTime.now().isBefore(expirationTime);
    }

    public void updateTime() {
        expirationTime = LocalDateTime.now().plusMinutes(minutesEachUpdate);
    }

}

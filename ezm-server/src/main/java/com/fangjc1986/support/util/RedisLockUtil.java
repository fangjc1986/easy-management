package com.fangjc1986.support.util;

import com.fangjc1986.support.exception.ServiceException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisLockUtil {

    private static final Long EXPIRE_TIME = 5L;
    private static final Long TIME_OUT = 10L;
    private static final String LOCK_PREFIX = "REDIS_LOCK_";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    /**
     * redis 加锁
     *
     * @param key
     * @param expireTime
     * @param timeout
     * @return
     */
    public void lock(String key, Long expireTime, Long timeout) {
        long timeOut = System.currentTimeMillis() * timeout * 1000;
        try {
            do {
                long expTime = System.currentTimeMillis() + expireTime * 1000;
                String expTimeStr = String.valueOf(expTime);
                if (redisTemplate.opsForValue().setIfAbsent(LOCK_PREFIX + key, expTimeStr)) {
                    return;
                }
                String currentLock = redisTemplate.opsForValue().get(LOCK_PREFIX + key);
                if (!StringUtils.isEmpty(currentLock) && Long.parseLong(currentLock) < System.currentTimeMillis()) {
                    String preLock = redisTemplate.opsForValue().getAndSet(LOCK_PREFIX + key, expTimeStr);
                    if (!StringUtils.isEmpty(preLock) && preLock.equals(currentLock)) {
                        return;
                    }
                }
            } while (System.currentTimeMillis() < timeOut);
        } catch (Exception ignore) {
        }
        throw new ServiceException("并发锁超时！");
    }

    /**
     * redis 加锁
     *
     * @param key
     */
    public void lock(String key) {
        lock(key, EXPIRE_TIME, TIME_OUT);
    }

    /**
     * 解锁
     *
     * @param key
     */
    public void unlock(String key) {
        try {
            redisTemplate.opsForValue().getOperations().delete(LOCK_PREFIX + key);
        } catch (Exception ignore) {

        }
    }
}

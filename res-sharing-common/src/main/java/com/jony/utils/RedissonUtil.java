package com.jony.utils;

import jakarta.annotation.Resource;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redisson分布式锁工具类
 */
@Component
public class RedissonUtil {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 加锁
     *
     * @param lockKey 锁的键
     * @return RLock
     */
    public RLock lock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        return lock;
    }

    /**
     * 带超时的锁
     *
     * @param lockKey 锁的键
     * @param timeout 超时时间 单位：秒
     */
    public RLock lock(String lockKey, long timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, TimeUnit.SECONDS);
        return lock;
    }

    /**
     * 带超时的锁
     *
     * @param lockKey 锁的键
     * @param unit    时间单位
     * @param timeout 超时时间
     */
    public RLock lock(String lockKey, TimeUnit unit, long timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, unit);
        return lock;
    }

    /**
     * 尝试获取锁
     *
     * @param lockKey  锁的键
     * @param waitTime 最多等待时间
     * @param unit     TimeUnit时间单位
     * @return boolean
     */
    public boolean tryLock(String lockKey, long waitTime, TimeUnit unit) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    /**
     * 尝试获取锁
     *
     * @param lockKey   锁的键
     * @param waitTime  最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     * @return boolean
     */
    public boolean tryLock(String lockKey, long waitTime, long leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }

    /**
     * 尝试获取锁
     *
     * @param lockKey   锁的键
     * @param unit      时间单位
     * @param waitTime  最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     * @return boolean
     */
    public boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    /**
     * 释放锁
     *
     * @param lockKey 锁的键
     */
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }

    /**
     * 若没用锁情况下，就不调用释放锁的代码，若有锁情况下才调用释放锁
     *
     * @param lockKey 锁的键
     */
    public void unlockIgnore(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        if (!lock.isLocked()) {
            return;
        }
        lock.unlock();
    }


    /**
     * 释放锁
     *
     * @param lock 锁
     */
    public void unlock(RLock lock) {
        lock.unlock();
    }

}

package com.jdkhome.blzo.ex.redission.actuator;

import com.jdkhome.blzo.ex.basic.configuration.ApplicationContextProvider;
import com.jdkhome.blzo.ex.basic.enums.BasicResponseError;
import com.jdkhome.blzo.ex.basic.exception.ServiceException;
import com.jdkhome.blzo.ex.redission.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.redisson.RedissonFairLock;
import org.redisson.RedissonMultiLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * 公平锁
 */
@Slf4j
public class FairLockActuator {

    private String key;

    private long wait = 2L;
    private long lease = 10L;

    public void exec(Consumer action) {
        RedissonClient redissonClient = ApplicationContextProvider.getBean(RedissonClient.class);

        RLock fairLock = redissonClient.getFairLock(key);

        try {
            boolean lockResult = fairLock.tryLock(wait, lease, TimeUnit.SECONDS);
            if (!lockResult) {
                log.warn("获取分布式公平锁失败 超时 : {} ", fairLock);
                throw new ServiceException(BasicResponseError.SERVER_BUSY);
            }

            log.debug("成功获取分布式公平锁 : {} ", fairLock);

            action.accept();

        } catch (ServiceException se) {
            log.warn("业务异常 当前占用分布式公平锁 : {} exception :{}  ", fairLock, se.getErrorMsg());
            throw se;
        } catch (InterruptedException e) {
            log.warn("InterruptedException 当前占用分布式公平锁 : {} ", fairLock);
            Thread.currentThread().interrupt();
            throw new ServiceException(BasicResponseError.SERVER_ERROR, e);
        } catch (Exception e) {
            log.warn("其他异常 当前占用分布式公平锁 : {} exception :{}  ", fairLock, e.getMessage());
            throw e;
        } finally {
            if (null != fairLock) {
                try {
                    fairLock.unlock();
                    log.debug("成功释放分布式公平锁 : {} ", fairLock);
                } catch (Exception e) {
                    log.warn("释放分布式锁失公平锁", e);
                }
            }
        }

    }


    public FairLockActuator(String key, long wait, long lease) {
        this.key = key;
        this.wait = wait;
        this.lease = lease;
    }

    public FairLockActuator(String key) {
        this.key = key;
    }

    static public FairLockActuator create(String key, long wait, long lease) {
        return new FairLockActuator(key, wait, lease);
    }

    static public FairLockActuator create(String key) {
        return new FairLockActuator(key);
    }
}

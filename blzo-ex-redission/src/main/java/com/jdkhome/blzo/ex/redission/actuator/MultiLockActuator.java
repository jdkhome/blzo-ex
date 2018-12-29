package com.jdkhome.blzo.ex.redission.actuator;

import com.jdkhome.blzo.ex.basic.configuration.ApplicationContextProvider;
import com.jdkhome.blzo.ex.basic.enums.BasicResponseError;
import com.jdkhome.blzo.ex.basic.exception.ServiceException;
import com.jdkhome.blzo.ex.redission.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.redisson.RedissonMultiLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 联锁
 */
@Slf4j
public class MultiLockActuator {

    private List<String> keys;

    private long wait = 2L;
    private long lease = 10L;

    public void exec(Consumer action) {
        RedissonClient redissonClient = ApplicationContextProvider.getBean(RedissonClient.class);

        List<RLock> rLockList = new ArrayList<>();
        keys.forEach(key -> rLockList.add(redissonClient.getLock(key)));

        RedissonMultiLock multiLock = new RedissonMultiLock(rLockList.toArray(new RLock[]{}));

        try {
            boolean lockResult = multiLock.tryLock(wait, lease, TimeUnit.SECONDS);
            if (!lockResult) {
                log.warn("获取分布式联锁失败 超时 : {} ", multiLock);
                throw new ServiceException(BasicResponseError.SERVER_BUSY);
            }

            log.debug("成功获取分布式联锁 : {} ", multiLock);

            action.accept();

        } catch (ServiceException se) {
            log.warn("业务异常 当前占用分布式锁 : {} exception :{}  ", multiLock, se.getErrorMsg());
            throw se;
        } catch (InterruptedException e) {
            log.warn("InterruptedException 当前占用分布式锁 : {} ", multiLock);
            Thread.currentThread().interrupt();
            throw new ServiceException(BasicResponseError.SERVER_ERROR, e);
        } catch (Exception e) {
            log.warn("其他异常 当前占用分布式锁 : {} exception :{}  ", multiLock, e.getMessage());
            throw e;
        } finally {
            if (null != multiLock) {
                try {
                    multiLock.unlock();
                    log.debug("成功释放分布式锁 : {} ", multiLock);
                } catch (Exception e) {
                    log.warn("释放分布式锁失败", e);
                }
            }
        }

    }

    public MultiLockActuator(List<String> keys, long wait, long lease) {
        this.keys = keys;
        this.wait = wait;
        this.lease = lease;
    }

    public MultiLockActuator(List<String> keys) {
        this.keys = keys;
    }

    static public MultiLockActuator create(List<String> keys, long wait, long lease) {
        return new MultiLockActuator(keys, wait, lease);
    }

    static public MultiLockActuator create(List<String> keys) {
        return new MultiLockActuator(keys);
    }

}

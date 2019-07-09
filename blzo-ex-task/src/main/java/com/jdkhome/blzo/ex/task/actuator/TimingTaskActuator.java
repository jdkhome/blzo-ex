package com.jdkhome.blzo.ex.task.actuator;


import com.jdkhome.blzo.ex.basic.configuration.ApplicationContextProvider;
import com.jdkhome.blzo.ex.basic.exception.ServiceException;
import com.jdkhome.blzo.ex.redission.actuator.FairLockActuator;
import com.jdkhome.blzo.ex.task.Consumer;
import com.jdkhome.blzo.ex.task.TaskStatusEnum;
import com.jdkhome.blzo.ex.task.constants.TaskCons;
import com.jdkhome.blzo.ex.basic.tools.TimeoutBlock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.*;

/**
 * author linkji.
 * create at 2019-07-08 16:46
 * 定时任务执行器
 */
@Slf4j
public class TimingTaskActuator {


    /**
     * 任务名
     */
    private String name;

    /**
     * 默认不等待
     */
    private long wait = 0L;

    /**
     * 默认3分钟超时时间
     */
    private long lease = 180L;

    public void exec(Consumer action) {
        log.debug("[BLZO-TASK] -> 开始执行任务:{}", name);
        // 首先拿到任务执行的锁
        try {
            FairLockActuator.create(String.format(TaskCons.TASK_LOCK, name), wait, lease).exec(() -> {
                StringRedisTemplate stringRedisTemplate = ApplicationContextProvider.getBean(StringRedisTemplate.class);

                // 检查任务状态
                TaskStatusEnum status = TaskStatusEnum.getByCode(stringRedisTemplate.opsForValue().get(String.format(TaskCons.TASK_STATUS, name)));

                if (!TaskStatusEnum.WAITING.equals(status)) {
                    log.debug("[BLZO-TASK] -> 任务:{} 已在执行中", name);
                    return;
                }

                // 设置状态
                stringRedisTemplate.opsForValue().set(String.format(TaskCons.TASK_STATUS, name), TaskStatusEnum.DOING.getCode(), lease, TimeUnit.SECONDS);

                try {
                    TimeoutBlock timeoutBlock = new TimeoutBlock(2 * 1000);//set timeout in milliseconds
                    Runnable block = () -> {
                        try {
                            action.accept();
                        } catch (ServiceException se) {
                            log.error("[BLZO-TASK] -> 任务:{} 执行中产生异常:{}", name, se.getErrorMsg(), se);
                        } catch (Exception e) {
                            log.error("[BLZO-TASK] -> 任务:{} 执行中产生异常:{}", name, e.getMessage(), e);
                        }
                    };

                    timeoutBlock.addBlock(block);// execute the runnable block

                } catch (Throwable e) {
                    //catch the exception here . Which is block didn't execute within the time limit
                    log.error("[BLZO-TASK] -> 任务:{} timeout", name);
                }


                // 设置状态
                stringRedisTemplate.opsForValue().set(String.format(TaskCons.TASK_STATUS, name), TaskStatusEnum.WAITING.getCode(), lease, TimeUnit.SECONDS);
            });
        } catch (ServiceException se) {
            log.info("[BLZO-TASK] -> 任务:{} 未能完成执行:{} ", name, se.getErrorMsg());
        } catch (Exception e) {
            log.info("[BLZO-TASK] -> 任务:{} 未能完成执行:{} ", name, e.getMessage(), e);
        }
        log.debug("[BLZO-TASK] -> 任务执行结束:{}", name);

    }


    public TimingTaskActuator(String name, long lease) {
        this.name = name;
        this.lease = lease;
    }

    public TimingTaskActuator(String name) {
        this.name = name;
    }

    static public TimingTaskActuator create(String name, long lease) {
        return new TimingTaskActuator(name, lease);
    }

    static public TimingTaskActuator create(String name) {
        return new TimingTaskActuator(name);
    }

}

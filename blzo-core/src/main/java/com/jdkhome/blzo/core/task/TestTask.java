package com.jdkhome.blzo.core.task;

import com.jdkhome.blzo.ex.task.actuator.TimingTaskActuator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * author linkji.
 * create at 2019-07-08 17:17
 */
@Slf4j
//@Component
public class TestTask {

    // 1秒1次
    @Scheduled(fixedDelay = 1000L)
    private void task1() {
        String name = "task";

        TimingTaskActuator.create(name, 1).exec(() -> {

            log.info("{} 1开始执行", name);
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                // e.printStackTrace();
                log.error("1sleep1 被中断");
            }
            log.info("{} 1执行完毕1", name);
            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                // e.printStackTrace();
                log.error("1sleep2 被中断");
            }

            log.info("{} 1执行完毕2", name);
            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                // e.printStackTrace();
                log.error("1sleep3 被中断");
            }

            log.info("{} 1执行完毕3", name);
        });

    }

    // 3秒1次
    @Scheduled(fixedDelay = 3000L)
    private void task3() {
        String name = "task";

        TimingTaskActuator.create(name, 1).exec(() -> {

            log.info("{} 3开始执行", name);
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                // e.printStackTrace();
                log.error("3sleep1 被中断");
            }
            log.info("{} 3执行完毕1", name);
            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                // e.printStackTrace();
                log.error("3sleep2 被中断");
            }

            log.info("{} 3执行完毕2", name);
            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                // e.printStackTrace();
                log.error("3sleep3 被中断");
            }

            log.info("{} 3执行完毕3", name);
        });

    }

}

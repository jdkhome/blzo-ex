package com.jdkhome.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * create by linkji on 11:06 上午 2020/3/27
 */
@Slf4j
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    /**
     * Springboot应用程序入口
     */
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("over.");
    }
}

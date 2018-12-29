package com.jdkhome.blzo.core.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class TestService {

    @Autowired
    OtherService otherService;

    public void testLock() {

        String asd = "fuck ass";

        //AtomicReference<String> result = new AtomicReference<>();
//
//        for (int i = 0; i <= 100; i++) {
//
//            int finalI = i;
//            new Thread(() -> {
//                MultiLockActuator.create(Arrays.asList("lock")).exec(() -> {
//                    log.info(otherService.otherMethod(asd + finalI));
//                });
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//            }).run();
//
//        }


        //log.info(result.get());
    }

}

package com.jdkhome.blzo.manage;

import com.jdkhome.blzo.ex.authj.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ComponentScan(basePackages = {"com.jdkhome.blzo.ex", "com.jdkhome.blzo"})
@EnableScheduling
@EnableTransactionManagement
@SpringBootApplication
public class ManageApplication implements CommandLineRunner {

    @Autowired
    AdminService adminService;

    /**
     * Springboot应用程序入口
     */
    public static void main(String[] args) {
        //  SpringApplication.run(ManageApplication.class, args);

        int[] a = {3, 2, 4};

        System.out.println(Arrays.toString(twoSum(a, 6)));


    }

    static class Data {

        public int index;
        public int value;
    }

    public static int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];

        List<Data> datas = new ArrayList<>(nums.length);

        for (int i = 0; i < nums.length; i++) {
            Data data = new Data();
            data.index = i;
            data.value = nums[i];
            datas.add(data);
        }

        datas = datas.stream().sorted((a, b) -> a.value < b.value ? -1 : 1).collect(Collectors.toList());

        for (int i = 0; i <= (nums.length / 2) + 1; i++) {
            for (int j = nums.length - 1; j > 0; j--) {

                if (datas.get(i).value + datas.get(j).value == target) {

                    result[0] = datas.get(i).index;
                    result[1] = datas.get(j).index;
                    return result;
                }
            }
        }

        return result;
    }

    @Override
    public void run(String... args) throws Exception {
        /**
         * 初始化Root用户
         */
        adminService.initRoot();
        log.info("over.");
    }
}

package com.jdkhome.blzo.ex.task;

import lombok.Getter;

/**
 * author link.ji
 * createTime 下午4:05 2018/5/10
 * 任务状态枚举
 */
@Getter
public enum TaskStatusEnum {

    WAITING("waiting", "等待中"),
    DOING("doing", "执行中"),
    ;


    String code;
    String name;

    TaskStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static TaskStatusEnum getByCode(String code) {

        //1 get all the enum constants
        TaskStatusEnum[] enumTypes = values();

        //2 judge if constants contains name
        for (TaskStatusEnum enumType : enumTypes) {
            if (enumType.getCode().equals(code)) {
                return enumType;
            }
        }

        // 没找到默认为Waiting
        return WAITING;
    }


}

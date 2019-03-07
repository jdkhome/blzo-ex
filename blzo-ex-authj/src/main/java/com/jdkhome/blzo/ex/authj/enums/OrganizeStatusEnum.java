package com.jdkhome.blzo.ex.authj.enums;


public enum OrganizeStatusEnum {

    INIT(0, "初始"), // 初始状态是希望用户登录后立即修改密码
    NORMAL(1, "正常"),
    FREEZE(-1, "冻结");

    Integer code;
    String name;

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    OrganizeStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static OrganizeStatusEnum getByCode(Integer code) {

        //1 get all the enum constants
        OrganizeStatusEnum[] enumTypes = values();

        //2 judge if constants contains name
        for (OrganizeStatusEnum enumType : enumTypes) {
            if (enumType.getCode().equals(code)) {
                return enumType;
            }
        }
        return null;
    }
}

package com.jdkhome.blzo.ex.basic.enums;

import lombok.Getter;

/**
 * author linkji.
 * create at 2019-08-28 09:55.
 * https://blog.csdn.net/shenenhua/article/details/79150053
 */
@Getter
public enum I18nEnums {

    ZH_CN("zh-cn", "简体中文(中国)"),
    ZH_HK("zh-hk", "繁体中文(香港)"),
    ZH_TW("zh-tw", "繁体中文(台湾地区)"),

    EN_US("en-us", "英语(美国)"),
    EN_WW("en-ww", "英语(全球)"),
    EN_AU("en-au", "英语(澳大利亚)"),
    EN_FI("en-fi", "英语(芬兰)"),
    EN_DK("en-dk", "英语(丹麦)"),
    EN_IL("en-il", "英语(以色列)"),
    EN_ZA("en-za", "英语(南非)"),
    EN_NO("en-no", "英语(挪威)"),
    EN_NZ("en-nz", "英语(新西兰)"),
    EN_PH("en-ph", "英语(菲律宾)"),
    EN_MY("en-my", "英语(马来西亚)"),
    EN_HK("en-hk", "英语(香港)"),
    EN_GB("en-gb", "英语(英国)"),
    EN_CA("en-ca", "英语(加拿大)"),
    EN_IE("en-ie", "英语(爱尔兰)"),
    EN_IN("en-in", "英语(印度)"),
    EN_SG("en-sg", "英语(新加坡)"),
    EN_ID("en-id", "英语(印度尼西亚)"),
    EN_TH("en-th", "英语(泰国)"),
    EN_XA("en-xa", "英语(阿拉伯)"),

    ES_ES("es-es", "西班牙语(西班牙)"),
    ES_US("es-us", "西班牙语(美国)"),
    ES_CO("es-co", "西班牙语(哥伦比亚)"),
    ES_LA("es-la", "西班牙语(拉丁美洲)"),
    ES_AR("es-ar", "西班牙语(阿根廷)"),
    ES_MX("es-mx", "西班牙语(墨西哥)"),
    ES_PR("es-pr", "西班牙语(波多黎各)"),

    FR_FR("fr_fr", "法语(法国)"),
    FR_CH("fr-ch", "法语(瑞士)"),
    FR_CA("fr-ca", "法语(加拿大)"),
    FR_LU("fr-lu", "法语(卢森堡)"),
    FR_BE("fr-be", "法语(比利时)"),

    DE_DE("de-de", "德语(德国)"),
    DE_CH("de-ch", "德语(瑞士)"),
    DE_AT("de-at", "德语(奥地利)"),

    PT_PT("pt-pt", "葡萄牙语(葡萄牙)"),
    PT_BR("pt-br", "葡萄牙语(巴西)"),

    NL_NL("nl-nl", "荷兰语(荷兰)"),
    NL_BE("nl-be", "荷兰语(比利时)"),

    JA_JP("ja-jp", "日语(日本)"),
    KO_KR("ko-kr", "韩文(韩国)"),

    IT_IT("it-it", "意大利语(意大利)"),
    NO_NO("no-no", "挪威语(挪威)"),
    TR_TR("tr-tr", "土耳其语(土耳其)"),
    SL_SL("sl-sl", "斯洛文尼亚语"),
    SV_SE("sv-se", "瑞典语(瑞典)"),
    FI_FI("fi-fi", "芬兰语(芬兰)"),
    DA_DK("da-dk", "丹麦语(丹麦)"),
    HE_IL("he-il", "希伯来语(以色列)"),
    RU_RU("ru-ru", "俄语(俄罗斯)"),
    EL_GR("el-gr", "希腊语(希腊)"),
    HU_HU("hu-hu", "匈牙利语(匈牙利)"),
    CS_CA("cs-cz", "捷克语(捷克共和国)"),
    PL_PL("pl-pl", "波兰语(波兰)"),

    ;

    String code;

    String region;

    I18nEnums(String code, String region) {
        this.code = code;
        this.region = region;
    }


    public static I18nEnums getByCode(String code) {

        //1 get all the enum constants
        I18nEnums[] enumTypes = values();

        //2 judge if constants contains name
        for (I18nEnums enumType : enumTypes) {
            if (enumType.getCode().equals(code)) {
                return enumType;
            }
        }
        return null;
    }

}

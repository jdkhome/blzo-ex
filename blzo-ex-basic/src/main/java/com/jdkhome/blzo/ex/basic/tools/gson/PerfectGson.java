package com.jdkhome.blzo.ex.basic.tools.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import java.math.BigDecimal;

/**
 * @author YC 获取一个完美gson：
 */
public class PerfectGson {
    private PerfectGson() {
    }

    /**
     * YC 2017年8月1日 下午5:09:34
     * <p>
     * Title: getGson
     * </p>
     * <p>
     * Description:
     * <h1>完美gson具有如下功能：</h1>
     * <p>
     * 1、serializeNulls（值为空也序列化）
     * </p>
     * <p>
     * 2、registerTypeAdapterFactory(new
     * NullStringToEmptyAdapterFactory())（值为null转换为""）
     * </p>
     * <p>
     * 3、.setExclusionStrategies(new TargetStrategy())（排除策略 ）
     * </p>
     *
     * @return
     */
    public static Gson getGson() {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls()
                .registerTypeAdapter(BigDecimal.class, (JsonSerializer<BigDecimal>) (value, typeOfSrc, context) -> {

                            if (value == null) {
                                return new JsonPrimitive("0"); // Convert NaN to zero
                            } else
                                return new JsonPrimitive(value.stripTrailingZeros().toPlainString());
                        }
                )
                .registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory<Object>())
                .setExclusionStrategies(new AnnotationExclusion()).create();
    }

    /**
     * YC 2017年8月11日 下午2:39:33
     * <p>
     * Title: getGson
     * </p>
     * <p>
     * Description: 带参
     * </p>
     *
     * @param strs
     * @return
     */
    public static Gson getGson(String[] strs) {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls()
                .registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory<Object>())
                .setExclusionStrategies(new FieldExclusion(strs)).create();
    }
}
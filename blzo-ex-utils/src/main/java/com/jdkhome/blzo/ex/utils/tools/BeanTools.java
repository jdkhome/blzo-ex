package com.jdkhome.blzo.ex.utils.tools;

import com.jdkhome.blzo.ex.basic.enums.BasicResponseError;
import com.jdkhome.blzo.ex.basic.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

/**
 * author linkji.
 * create at 2019-06-22 15:02
 */
@Slf4j
public class BeanTools {


    public static <E, T> T to(E res, Class<T> clazz) {
        T tar = null;

        if (res != null) {
            try {
                tar = clazz.getDeclaredConstructor().newInstance();
                BeanUtils.copyProperties(res, tar);
            } catch (Exception e) {
                log.error("[BeanTools]to -> 实例化失败", e);
                throw new ServiceException(BasicResponseError.SERVER_ERROR, "未知错误，实例化失败");
            }
        }

        return tar;
    }
}
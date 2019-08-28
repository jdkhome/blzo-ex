package com.jdkhome.blzo.ex.basic.i18n;

import com.jdkhome.blzo.ex.basic.constants.BasicSystemConstants;
import com.jdkhome.blzo.ex.basic.enums.I18nEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created by jdk on 19/08/28.
 * 国际化参数解析器
 */
@Slf4j
@Component
public class I18nMethodArgumentResolver implements HandlerMethodArgumentResolver {


    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        //如果参数类型是Integer并包含@CurrentUser注解才执行解析
        if (methodParameter.getParameterType().isAssignableFrom(I18nEnums.class)) {
            return true;
        }

        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {


        return I18nEnums.getByCode(nativeWebRequest.getHeader(BasicSystemConstants.i18n));

    }
}

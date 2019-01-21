package com.jdkhome.blzo.ex.usignin;

import com.jdkhome.blzo.ex.usignin.annotation.CurrentUser;
import com.jdkhome.blzo.ex.usignin.constants.UsigninConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created by jdk on 17/9/6.
 * 自定义参数解析器，对添加了@CurrentUser注解的方法解析出UserBase对象
 */
@Slf4j
@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {


//    @Autowired
//    UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        //如果参数类型是Integer并包含@CurrentUser注解才执行解析
        if (
//                (methodParameter.getParameterType().isAssignableFrom(Integer.class)
//                || methodParameter.getParameterType().isAssignableFrom(String.class))
//                &&
                methodParameter.hasParameterAnnotation(CurrentUser.class)) {
            return true;
        }

        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {

        String currentUserId = (String) nativeWebRequest.getAttribute(UsigninConstants.USER_ID, RequestAttributes.SCOPE_REQUEST);


        log.debug("currentUserId:{}", currentUserId);


        if (methodParameter.getParameterType().isAssignableFrom(Integer.class)) {
            return currentUserId == null ? null : Integer.parseInt(currentUserId);
        }


        return currentUserId;


        // 这里currentUserId是有可能为空的(redis里面错误数据之类的)

        //throw new MissingServletRequestPartException(SystemCons.USER_ID);
    }
}

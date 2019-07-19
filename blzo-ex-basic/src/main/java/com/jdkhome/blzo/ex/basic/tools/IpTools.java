package com.jdkhome.blzo.ex.basic.tools;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class IpTools {
    private IpTools() {
    }

    /**
     * 获取真实的IP，尤其是经过反向代理处理过的ip
     *
     * @Return
     */
    public static String getIp(HttpServletRequest request) {

        //1 优先从X-Forwarded-For
        String ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }

        //2 其次从X-Real-IP中获取ip
        ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }



}
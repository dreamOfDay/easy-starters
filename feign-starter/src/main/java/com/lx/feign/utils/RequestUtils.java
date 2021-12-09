package com.lx.feign.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: jyu
 * @Date: 2021/10/10
 * @Description: 请求工具
 **/
public class RequestUtils {

    /**
     * 得到与当前线程绑定request
     */
    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        if(requestAttributes !=null && requestAttributes instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes)requestAttributes).getRequest();
        }
        return null;
    }

    /**
     * 得到与当前线程绑定的response
     */
    public static HttpServletResponse getResponse() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        if(requestAttributes !=null && requestAttributes instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes)requestAttributes).getResponse();
        }
        return null;
    }

}

package com.lx.mapper.interceptor;

import com.github.pagehelper.PageHelper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: yuj
 * @Date: 2022/2/15
 * @Describe:
 */

public class PageLocalWebInterceptor implements HandlerInterceptor {
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        PageHelper.clearPage();
    }
}

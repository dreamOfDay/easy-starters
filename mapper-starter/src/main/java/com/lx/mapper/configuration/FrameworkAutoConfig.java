package com.lx.mapper.configuration;

import com.lx.mapper.interceptor.PageLocalWebInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: yuj
 * @Date: 2022/2/15
 * @Describe:
 */
@Configuration
public class FrameworkAutoConfig implements WebMvcConfigurer {

    @Bean
    public PageLocalWebInterceptor pageLocalWebInterceptor(){
        return new PageLocalWebInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(pageLocalWebInterceptor());
    }
}

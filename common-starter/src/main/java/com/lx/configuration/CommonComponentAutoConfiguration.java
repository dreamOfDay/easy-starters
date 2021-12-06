package com.lx.configuration;

import com.lx.components.SpringContextUtils;
import com.lx.exception.handler.GlobalExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @Author: jyu
 * @Date: 2021/10/10
 * @Description:
 **/
@Configuration
public class CommonComponentAutoConfiguration {
    @Bean
    public SpringContextUtils springContextUtils() {
        return new SpringContextUtils();
    }

    @Bean
    @Primary
    public GlobalExceptionHandler defaultExceptionHandler(){
        return new GlobalExceptionHandler();
    }

}

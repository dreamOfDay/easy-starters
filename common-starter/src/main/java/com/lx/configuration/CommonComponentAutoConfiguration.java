package com.lx.configuration;

import com.lx.components.SimpleDateConverter;
import com.lx.components.SpringContextUtils;
import com.lx.exception.handler.GlobalExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

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
    @ConditionalOnMissingBean(name = "defaultDateConverter")
    public Converter<String, Date> defaultDateConverter() {
        return new SimpleDateConverter();
    }

    @Bean
    @Primary
    public GlobalExceptionHandler defaultExceptionHandler(){
        return new GlobalExceptionHandler();
    }

}

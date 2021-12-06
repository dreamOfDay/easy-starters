package com.lx.mapper.configuration;

import com.lx.components.SpringContextUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: jyu
 * @Date: 2021/10/10
 * @Description:
 **/
@Configuration
public class MapperAutoConfiguration {

    @Bean
    public SpringContextUtils springContextUtil() {
        return new SpringContextUtils();
    }

}

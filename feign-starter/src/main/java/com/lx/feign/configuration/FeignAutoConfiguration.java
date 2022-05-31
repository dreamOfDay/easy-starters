package com.lx.feign.configuration;

import com.lx.feign.LocalFirstLoadBalancer;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @Author: jyu
 * @Date: 2021/10/10
 * @Description:
 **/
@Configuration
@AutoConfigureBefore({RibbonAutoConfiguration.class})
public class FeignAutoConfiguration {

    /**
     * dev 开发环境本机优先
     */
    @Bean
    @Profile("dev")
    public LocalFirstLoadBalancer localFirstLoadBalancer(){
        return new LocalFirstLoadBalancer();
    }

}

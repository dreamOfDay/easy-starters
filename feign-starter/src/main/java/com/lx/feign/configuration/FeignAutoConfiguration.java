package com.lx.feign.configuration;

import com.lx.feign.LoadBalancerRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: jyu
 * @Date: 2021/10/10
 * @Description:
 **/
@Configuration
@AutoConfigureBefore({RibbonAutoConfiguration.class})
public class FeignAutoConfiguration {

    @Bean
    public IRule ribbonRule() {
        return new LoadBalancerRule();
    }

}

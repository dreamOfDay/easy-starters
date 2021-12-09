package com.lx.redis.configuration;

import com.lx.redis.utils.StringRedisTemplateUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Describe
 * @Author yuj
 * @Date 2021/10/10
 */
@Configuration
public class RedisAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "stringRedisTemplateUtils")
    public StringRedisTemplateUtils LxRedisClient() {
        return new StringRedisTemplateUtils();
    }

}

package com.lx.redis.configuration;

import com.lx.redis.aspect.CacheAspect;
import com.lx.redis.aspect.LockAspect;
import com.lx.redis.utils.StringRedisTemplateUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

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

    @Bean
    public CacheAspect cacheAspect(){
        return new CacheAspect();
    }
    @Bean
    public LockAspect lockAspect(){
        return new LockAspect();
    }

}

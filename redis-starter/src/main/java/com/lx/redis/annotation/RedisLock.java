package com.lx.redis.annotation;

import java.lang.annotation.*;

/**
 * 给方法加redission分布式锁
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RedisLock {
//    @AliasFor("value")
    String baseKey() default "";
//    @AliasFor("baseKey")
    String value() default  "";
    int maxWaitSeconds() default 0;
    int maxLockSeconds() default 60;
}

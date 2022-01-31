package com.lx.redis.annotation;

import java.lang.annotation.*;


/**
 * 将返回值放入缓存中
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface AddCache {
//    @AliasFor("value")
    String baseKey() default "";
//    @AliasFor("baseKey")
    String value() default  "";
    int expiredSeconds() default -1;
}

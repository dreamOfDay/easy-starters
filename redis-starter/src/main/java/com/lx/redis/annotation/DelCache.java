package com.lx.redis.annotation;


import java.lang.annotation.*;


/**
 * 无异常则删除缓存
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DelCache {
//    @AliasFor("baseKey")
    String value() default "";
//    @AliasFor("value")
    String baseKey() default "";
}

package com.lx.redis.annotation;

import java.lang.annotation.*;


/**
 * 用以构造合适的key
 * 规则是：baseKey:field(value)
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface LockKey {
//    @AliasFor("field")
    String value() default "";
//    @AliasFor("value")
    String field() default "";
}

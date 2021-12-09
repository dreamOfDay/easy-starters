package com.lx.annotation;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

/**
 * @Author: yuj
 * @Date: 2021/12/9
 * @Describe: 可以用此注解对同类型的bean注入时进行分组，依赖查找时也可以通过此注解区分对应组的bean
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Qualifier
public @interface CommonGroup {
}

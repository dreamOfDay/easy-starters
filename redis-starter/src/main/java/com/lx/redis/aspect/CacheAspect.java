package com.lx.redis.aspect;


import com.lx.redis.annotation.AddCache;
import com.lx.redis.annotation.CacheKey;
import com.lx.redis.annotation.DelCache;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.concurrent.TimeUnit;


/**
 * 一个添加缓存与删除缓存的切面
 */
@Aspect
public class CacheAspect {

    @Autowired
    private RedisTemplate redisTemplate;

    @Pointcut("@annotation(com.lx.redis.annotation.AddCache)")
    public void addCachePointCut(){}
    @Pointcut("@annotation(com.lx.redis.annotation.DelCache)")
    public void delCachePointCut(){}

    @Around("addCachePointCut()")
    public Object handlerAddCache(ProceedingJoinPoint joinPoint) throws Throwable{
        // build key
        MethodSignature signature = (MethodSignature) (joinPoint.getSignature());
        Method method = signature.getMethod();
        AddCache addCache = method.getAnnotation(AddCache.class);
        Parameter[] parameters = method.getParameters();
        String baseKey =addCache.baseKey();
        if(StringUtils.isEmpty(baseKey)){
            baseKey = addCache.value();
        }
        StringBuilder builder = new StringBuilder(baseKey);
        Object[] args = joinPoint.getArgs();
        for(int i=0; i<args.length; i++){
            Parameter parameter = parameters[i];
            CacheKey cacheKey = parameter.getAnnotation(CacheKey.class);
            Object arg = args[i];
            if(BeanUtils.isSimpleValueType(parameter.getType())){
                builder.append( ":" + args.toString());
                continue;
            }
            String field =cacheKey.field();
            if(StringUtils.isEmpty(field)){
                field = cacheKey.value();
            }
            if(cacheKey !=null ){

                Field declaredField = parameter.getType().getDeclaredField(field);
                declaredField.setAccessible(true);
                Object fieldKey = declaredField.get(arg);
                declaredField.setAccessible(false);
                builder.append( ":" + fieldKey.toString());
            }
        }
        // 构造key
        String key = builder.toString();
        // getCacheValue
        Object o = redisTemplate.opsForValue().get(key);
        if(o != null){
            return o;
        }

        // 如果无数据则执行将返回值放入缓存后返回
        Object value = joinPoint.proceed();
        if(addCache.expiredSeconds() > 0){
            redisTemplate.opsForValue().set(key,value,addCache.expiredSeconds(), TimeUnit.SECONDS);
        }else {
            redisTemplate.opsForValue().set(key,value);
        }

        return value;
    }

    @After("delCachePointCut()")
    public void handleDelCache(JoinPoint joinPoint) throws Throwable{
        // build key
        MethodSignature signature = (MethodSignature) (joinPoint.getSignature());
        Method method = signature.getMethod();
        DelCache delCache = method.getAnnotation(DelCache.class);
        Parameter[] parameters = method.getParameters();
        String baseKey =delCache.baseKey();
        if(StringUtils.isEmpty(baseKey)){
            baseKey = delCache.value();
        }
        StringBuilder builder = new StringBuilder(baseKey);
        Object[] args = joinPoint.getArgs();
        for(int i=0; i<args.length; i++){
            Parameter parameter = parameters[i];
            CacheKey cacheKey = parameter.getAnnotation(CacheKey.class);
            Object arg = args[i];
            if(BeanUtils.isSimpleValueType(parameter.getType())){
                builder.append( ":" + args.toString());
                continue;
            }
            String field =cacheKey.field();
            if(StringUtils.isEmpty(field)){
                field = cacheKey.value();
            }
            if(cacheKey !=null ){

                Field declaredField = parameter.getType().getDeclaredField(field);
                declaredField.setAccessible(true);
                Object fieldKey = declaredField.get(arg);
                declaredField.setAccessible(false);
                builder.append( ":" + fieldKey.toString());
            }
        }
        // 构造key
        String key = builder.toString();
        redisTemplate.delete(key);
    }

}

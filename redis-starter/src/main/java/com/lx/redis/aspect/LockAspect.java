package com.lx.redis.aspect;

import com.lx.redis.annotation.CacheKey;
import com.lx.redis.annotation.RedisLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.concurrent.TimeUnit;

@Aspect
public class LockAspect {
    @Autowired
    private RedissonClient redissonClient;

    @Pointcut("@annotation(com.lx.redis.annotation.RedisLock)")
    public void lockPointCut(){}

    @Around("lockPointCut()")
    public Object handleLock(ProceedingJoinPoint joinPoint) throws Throwable{
        // build key
        MethodSignature signature = (MethodSignature) (joinPoint.getSignature());
        Method method = signature.getMethod();
        RedisLock redisLock = method.getAnnotation(RedisLock.class);
        Parameter[] parameters = method.getParameters();
        String baseKey =redisLock.baseKey();
        if(StringUtils.isEmpty(baseKey)){
            baseKey = redisLock.value();
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
        RLock lock = redissonClient.getLock(key);
        int maxWaitSeconds = redisLock.maxWaitSeconds();
        int maxLockSeconds = redisLock.maxLockSeconds();
        Object returnValue = null;
        try {
            if(lock.tryLock(maxWaitSeconds,maxLockSeconds, TimeUnit.SECONDS)){
                returnValue = joinPoint.proceed();
            }else {
                throw new RuntimeException("无法获取redis分布式锁key："+key);
            }
        }finally {
            if(lock.isLocked()){
                lock.unlock();
            }
        }
        return returnValue;
    }

}

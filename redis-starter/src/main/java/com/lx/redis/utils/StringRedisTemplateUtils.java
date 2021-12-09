package com.lx.redis.utils;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.alibaba.fastjson.JSON;

/**
 * 默认单位是秒，默认过期时长为30天；使用FastJson序列化。
 * <br>
 * the default time unit is second ,default timeout is 30 days。 use fastjson to serialize
 *
 * @author tlrobot
 */
public class StringRedisTemplateUtils {
    // 30天
    public final long DEFAULT_EXP_TIME = 30 * 24 * 60 * 60;
    //单位是秒
    public final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;

    @Value("${spring.application.name:default}")
    private String APP_NAME;

    @Value("${lx.redis.separator:_}")
    private String SEPARATOR;

    /**
     * @return 分隔符，默认_
     */
    public String getSeparator() {
        return SEPARATOR;
    }

    /**
     * @return spring.application.name}默认default
     */
    public String getAppName() {
        return APP_NAME;
    }

    public String getPrefix(){
        return getAppName() + getSeparator();
    }

    private StringRedisTemplate stringRedisTemplate;

    public StringRedisTemplate getStringRedisTemplate() {
        return stringRedisTemplate;
    }

    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * key会是Appname+分隔符+key
     *
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        this.set(key, value, DEFAULT_EXP_TIME);
    }

    /**
     * @param seconds 过期时间
     */
    public void set(String key, Object value, long seconds) {
        this.set(key, value, seconds, DEFAULT_TIME_UNIT);
    }

    /**
     * @param timeUnit 时间单位，默认秒
     */
    public void set(String key, Object value, long seconds, TimeUnit timeUnit) {
        String valueStr = JSON.toJSONString(value);
        stringRedisTemplate.opsForValue().set(getPrefix() + key, valueStr, seconds, timeUnit);
    }

    /**
     * 永不失效
     */
    public void setForever(String key, Object value) {
        String valueStr = JSON.toJSONString(value);
        stringRedisTemplate.opsForValue().set(getPrefix() + key, valueStr);
    }

    /**
     * 通过 key 获取 value
     * @param key
     * @return
     */
    public String get(String key) {
        String value = stringRedisTemplate.opsForValue().get(getPrefix() + key);
        return value;
    }

    /**
     * 通过 key 获取 value 并反序列化
     */
    public <T> T get(String key, Class<T> clazz) {
        String valueStr = stringRedisTemplate.opsForValue().get(getPrefix() + key);
        T value = JSON.parseObject(valueStr, clazz);
        return value;
    }
}


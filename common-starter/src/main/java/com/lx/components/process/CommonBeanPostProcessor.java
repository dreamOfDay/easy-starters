package com.lx.components.process;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.text.SimpleDateFormat;

/**
 * @Author: jyu
 * @Date: 2023/2/3
 * @Description: 公共的 BeanPostProcessor
 **/
public class CommonBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (bean instanceof MappingJackson2HttpMessageConverter){
            ObjectMapper mapper = ((MappingJackson2HttpMessageConverter) bean).getObjectMapper();
            // 返回日期格式的数据时的默认返回格式,可以省略 @JsonFormat 等注解
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            // Long 转 String 避免编译器造成的精度丢失
            SimpleModule simpleModule = new SimpleModule();
            simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
            simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
            mapper.registerModule(simpleModule);

        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

}

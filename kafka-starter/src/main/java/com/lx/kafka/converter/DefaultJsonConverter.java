package com.lx.kafka.converter;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONValidator;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.log.LogAccessor;

/**
 * @Author: jyu
 * @Date: 2022/4/13
 * @Description:
 **/
public class DefaultJsonConverter implements Converter<ConsumerRecord, Object> {
    protected final LogAccessor logger = new LogAccessor(LogFactory.getLog(getClass()));

    @Override
    public Object convert(ConsumerRecord record) {
        String value = (String)record.value();
        // 校验json字符串类型并转换
        JSONValidator.Type jsonType = JSONValidator.from(value).getType();
        switch (jsonType){
            case Value:
                return value;
            case Object:
                return JSONObject.parseObject(value);
            case Array:
                return JSONObject.parseArray(value);
            default:
                logger.error(String.format("record.value format error : %s", value));
                throw new MessageConverter.TypeNotSupportException(String.format("record.value format error : %s", value));
        }
    }
}

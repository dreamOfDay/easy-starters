package com.lx.kafka.serialization;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

/**
 * @Author: jyu
 * @Date: 2022/4/13
 * @Description: {@link org.apache.kafka.clients.producer.KafkaProducer}的 valueSerializer
 **/
public class JsonSerializer implements Serializer<Object> {
    @Override
    public byte[] serialize(String topic, Headers headers, Object data) {
        return this.serialize(topic, data);
    }

    @Override
    public byte[] serialize(String s, Object data) {
        return JSON.toJSONBytes(data);
    }
}

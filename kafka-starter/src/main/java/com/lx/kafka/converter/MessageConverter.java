package com.lx.kafka.converter;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaNull;
import org.springframework.kafka.support.converter.MessagingMessageConverter;

import java.lang.reflect.Type;

/**
 * @Author: jyu
 * @Date: 2022/4/13
 * @Description: 消息转换器，提供默认的转换json类型的消息
 **/
@Configuration
public class MessageConverter extends MessagingMessageConverter {

    private Converter converter;
    
    public MessageConverter() {
        this.converter = new DefaultConverter();
    }

    public Converter getConverter() {
        return converter;
    }

    public void setConverter(Converter converter) {
        this.converter = converter;
    }

    @Override
    protected Object extractAndConvertValue(ConsumerRecord<?, ?> record, Type type) {
        return record.value() == null ? KafkaNull.INSTANCE : this.converter.converter(record);
    }

    @FunctionalInterface
    public interface Converter{
        Object converter(ConsumerRecord<?, ?> record);
    }

    public static class TypeNotSuppostException extends RuntimeException{
        private static final long serialVersionUID = -5101214195716534496L;

        public TypeNotSuppostException(String message) {
            super(message);
        }
    }

}


package com.lx.kafka.converter;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.kafka.support.KafkaNull;
import org.springframework.kafka.support.converter.MessagingMessageConverter;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;

/**
 * @Author: jyu
 * @Date: 2022/4/13
 * @Description: 消息转换器，提供默认的转换json类型的消息
 **/
@Configuration
public class MessageConverter extends MessagingMessageConverter {

    private Converter converter;

    @PostConstruct
    public void init(){
        this.converter = new DefaultJsonConverter();
    }
    
    public Converter getConverter() {
        return converter;
    }

    public void setConverter(Converter converter) {
        this.converter = converter;
    }

    @Override
    protected Object extractAndConvertValue(ConsumerRecord<?, ?> record, Type type) {
        return record.value() == null ? KafkaNull.INSTANCE : this.converter.convert(record);
    }

    public static class TypeNotSuppostException extends RuntimeException{
        private static final long serialVersionUID = -5101214195716534496L;

        public TypeNotSuppostException(String message) {
            super(message);
        }
    }

}


package com.lx.kafka.configuration;

import com.lx.kafka.converter.MessageConverter;
import com.lx.kafka.serialization.JsonSerializer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.MessagingMessageConverter;

/**
 * @Author: jyu
 * @Date: 2022/4/13
 * @Description:
 **/
@Configuration
@AutoConfigureAfter({KafkaAutoConfiguration.class})
public class LxKafkaAutoConfiguration {

    @Bean
    public ProducerFactory<?, ?> defaultKafkaProducerFactory(ProducerFactory producerFactory){
        // reason whey can cast producerFactory to DefaultKafkaProducerFactory is this.autoConfiguration happens after KafkaAutoConfiguration
        // if you want to enable your own kafka config, please don't use this dependency
        ((DefaultKafkaProducerFactory) producerFactory).setValueSerializer(new JsonSerializer());
        return producerFactory;
    }

    @Bean
    public MessagingMessageConverter messagingMessageConverter(){
        return new MessageConverter();
    }

}

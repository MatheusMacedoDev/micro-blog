package com.macedo.email_service.services.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Slf4j
public class RabbitMqConfig {

    public static final String EMAIL_QUEUE_NAME = "email-queue";

    @Bean
    public SimpleMessageConverter simpleMessageConverter() {
        SimpleMessageConverter converter = new SimpleMessageConverter();
        converter.setAllowedListPatterns(List.of("com.macedo.email_service.*", "java.util.*", "java.sql.*"));
        return converter;
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE_NAME);
    }

}

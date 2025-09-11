package com.macedo.email_service.services.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RabbitMqConfig {

    public static final String EMAIL_QUEUE_NAME = "email-queue";

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE_NAME);
    }

}

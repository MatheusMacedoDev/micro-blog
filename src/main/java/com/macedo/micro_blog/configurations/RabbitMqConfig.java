package com.macedo.micro_blog.configurations;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqConfig {

    @Bean
    public ApplicationRunner runner(AmqpTemplate template) {
        return args -> template.convertAndSend("email-queue", "Mensagem qualquer...");
    }

    @Bean
    public Queue emailQueue() {
        return new Queue("email-queue");
    }

    @RabbitListener(queues = "email-queue")
    public void listen(@Payload String payload) {
        System.out.println(payload);
    }

}

package com.macedo.micro_blog.infra.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitEmailConsumer {

    @RabbitListener(queues = RabbitMqConfig.EMAIL_QUEUE_NAME)
    public void listen(@Payload EmailDto emailDto) {
        log.info("Mensagem recebida: {} - {} - {} - {}",
            emailDto.emailTo, emailDto.emailFrom, emailDto.emailSubject, emailDto.emailBody);
    }

}

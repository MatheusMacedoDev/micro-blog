package com.macedo.micro_blog.infra.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitEmailPublisher  implements  MessagePublisher {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publish(Object emailData) {
        log.info("Enviando mensagem: {} - Fila: {}", emailData, RabbitMqConfig.EMAIL_QUEUE_NAME);
        rabbitTemplate.convertAndSend(RabbitMqConfig.EMAIL_QUEUE_NAME, emailData);
    }

}

package com.macedo.micro_blog.infra.rabbitmq;

import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.w3c.dom.Text;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitEmailPublisher  implements  MessagePublisher {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publish(Object emailMessage) {
        log.info("Enviando mensagem: {} - Fila: {}", emailMessage, RabbitMqConfig.EMAIL_QUEUE_NAME);
        rabbitTemplate.convertAndSend(RabbitMqConfig.EMAIL_QUEUE_NAME, emailMessage);
    }

}

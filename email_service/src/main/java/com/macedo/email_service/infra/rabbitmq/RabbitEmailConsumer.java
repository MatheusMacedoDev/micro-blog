package com.macedo.email_service.infra.rabbitmq;

import com.macedo.email_service.dtos.PostDTO;
import com.macedo.email_service.mail.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
@Slf4j
@RequiredArgsConstructor
public class RabbitEmailConsumer {

    private final MailService mailService;

    @RabbitListener(queues = RabbitMqConfig.EMAIL_QUEUE_NAME)
    public void listen(@Payload PostDTO post) throws MessagingException, UnsupportedEncodingException {
        log.info("Mensagem recebida do post \"{}\". Enviando e-mail para os autores...", post.title());

        mailService.handleNewPostEmail(post);
    }

}

package com.macedo.email_service.services.rabbitmq;

import com.macedo.email_service.domain.entities.Author;
import com.macedo.email_service.domain.repositories.AuthorRepository;
import com.macedo.email_service.dtos.PostDTO;
import com.macedo.email_service.services.mail.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class RabbitEmailConsumer {

    private final MailService mailService;
    private final AuthorRepository authorRepository;

    @RabbitListener(queues = RabbitMqConfig.EMAIL_QUEUE_NAME)
    public void listen(@Payload PostDTO post) throws MessagingException, UnsupportedEncodingException {
        log.info("Mensagem recebida do post \"{}\". Enviando e-mail para os autores...", post.title());

        List<Author> authors = authorRepository.findAll();
        mailService.sendNewPostEmail(post, authors);
    }

}

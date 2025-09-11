package com.macedo.email_service.mail;

import com.macedo.email_service.dtos.AuthorDTO;
import com.macedo.email_service.dtos.PostDTO;
import com.macedo.email_service.infra.api_service.ApiServiceClient;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MailService {

    private final Environment environment;
    private final JavaMailSender mailSender;
    private final TemplateEngine hmtlTemplateEngine;

    private final ApiServiceClient apiServiceClient;

    public void handleNewPostEmail(PostDTO post) throws MessagingException, UnsupportedEncodingException {
        List<AuthorDTO> authors = apiServiceClient.getAllAuthors();
        sendNewPostEmail(post, authors);
    }

    private void sendNewPostEmail(PostDTO post, List<AuthorDTO> authors) throws MessagingException, UnsupportedEncodingException {

        String mailFrom = environment.getProperty("spring.mail.username");
        String mailFromName = environment.getProperty("mail.from.name", "Identity");

        final MimeMessage message = this.mailSender.createMimeMessage();
        final MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

        InternetAddress[] internetAddresses = new InternetAddress[authors.size()];

        for (int i = 0; i < authors.size(); i++) {
            internetAddresses[i] = new InternetAddress(authors.get(i).email());
        }

        messageHelper.setBcc(internetAddresses);
        messageHelper.setSubject(NewPostMail.MAIL_SUBJECT);
        messageHelper.setFrom(new InternetAddress(mailFrom, mailFromName));

        final Context context = new Context(LocaleContextHolder.getLocale());
        context.setVariable("authorName", post.author().name());
        context.setVariable("postTitle", post.title());
        context.setVariable("publishedAtDate", NewPostMail.getFormatedPublishedDate(post));
        context.setVariable("publishedAtTime", NewPostMail.getFormatedPublishedTime(post));

        final String htmlContent = this.hmtlTemplateEngine.process(NewPostMail.TEMPLATE_NAME, context);

        messageHelper.setText(htmlContent, true);

        mailSender.send(message);
    }

}
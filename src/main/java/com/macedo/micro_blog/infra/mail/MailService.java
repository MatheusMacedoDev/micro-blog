package com.macedo.micro_blog.infra.mail;

import com.macedo.micro_blog.domain.entities.Author;
import com.macedo.micro_blog.domain.entities.Post;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
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

    private final NewPostMail newPostMail;

    public void sendNewPostEmail(Post post, List<Author> authors) throws MessagingException, UnsupportedEncodingException {

        String mailFrom = environment.getProperty("spring.mail.properties.smtp.from");
        String mailFromName = environment.getProperty("mail.from.name", "Identity");

        final MimeMessage message = this.mailSender.createMimeMessage();
        final MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

        InternetAddress[] internetAddresses = (InternetAddress[]) authors.stream()
            .map(author -> {
                try {
                    return new InternetAddress((author.getEmail()));
                } catch (AddressException e) {
                    throw new RuntimeException(e);
                }
            }).toArray();

        messageHelper.setTo(internetAddresses);
        messageHelper.setSubject(NewPostMail.MAIL_SUBJECT);
        messageHelper.setFrom(new InternetAddress(mailFrom, mailFromName));

        final Context context = new Context(LocaleContextHolder.getLocale());
        context.setVariable("authorName", post.getAuthor().getName());
        context.setVariable("postTitle", post.getTitle());
        context.setVariable("publishedAtDate", NewPostMail.getFormatedPublishedDate(post));
        context.setVariable("publishedAtTime", NewPostMail.getFormatedPublishedTime(post));

        final String htmlContent = this.hmtlTemplateEngine.process(NewPostMail.TEMPLATE_NAME, context);

        messageHelper.setText(htmlContent);

        mailSender.send(message);
    }

}
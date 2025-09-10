package com.macedo.email_service.services.mail;

import com.macedo.email_service.dtos.PostDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class NewPostMail {

    public static final String TEMPLATE_NAME = "new_post";
    public static final String MAIL_SUBJECT = "Houve uma nova publicação, confira!";

    public static String getFormatedPublishedDate(PostDTO post) {
        LocalDateTime publishedAtDateTime = post.publishedAt().toLocalDateTime();

        return publishedAtDateTime
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public static String getFormatedPublishedTime(PostDTO post) {
        LocalDateTime publishedAtDateTime = post.publishedAt().toLocalDateTime();

        return publishedAtDateTime
                .format(DateTimeFormatter.ofPattern("HH:mm"));
    }

}

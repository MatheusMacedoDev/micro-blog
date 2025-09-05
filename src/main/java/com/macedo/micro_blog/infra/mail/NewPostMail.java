package com.macedo.micro_blog.infra.mail;

import com.macedo.micro_blog.domain.entities.Post;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class NewPostMail {

    public static final String TEMPLATE_NAME = "new post";
    public static final String MAIL_SUBJECT = "Houve uma nova publicação, confira!";

    public static String getFormatedPublishedDate(Post post) {
        LocalDateTime publishedAtDateTime = post.getPublishedAt().toLocalDateTime();

        return publishedAtDateTime
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public static String getFormatedPublishedTime(Post post) {
        LocalDateTime publishedAtDateTime = post.getPublishedAt().toLocalDateTime();

        return publishedAtDateTime
                .format(DateTimeFormatter.ofPattern("HH:mm"));
    }

}

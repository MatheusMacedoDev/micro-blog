package com.macedo.email_service.dtos;

import com.macedo.email_service.domain.entities.Post;

import java.io.Serializable;
import java.sql.Timestamp;

public record PostDTO(int id, String title, AuthorDTO author, String content, Timestamp publishedAt) implements Serializable {

    public PostDTO(Post post) {
        this(
            post.getId(),
            post.getTitle(),
            new AuthorDTO(post.getAuthor()),
            post.getContent(),
            post.getPublishedAt()
        );
    }

}

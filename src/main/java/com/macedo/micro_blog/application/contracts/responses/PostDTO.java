package com.macedo.micro_blog.application.contracts.responses;

import com.macedo.micro_blog.domain.entities.Post;

import java.sql.Timestamp;

public record PostDTO(int id, String title, AuthorDTO author, String content, Timestamp publishedAt) {

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

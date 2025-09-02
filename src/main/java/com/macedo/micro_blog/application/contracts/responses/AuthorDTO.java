package com.macedo.micro_blog.application.contracts.responses;

import com.macedo.micro_blog.domain.entities.Author;

public record AuthorDTO(int id, String name) {

    public AuthorDTO(Author author) {
        this(
            author.getId(),
            author.getName()
        );
    }

}

package com.macedo.micro_blog.application.contracts.responses;

import com.macedo.micro_blog.domain.entities.Author;

import java.io.Serializable;

public record AuthorDTO(int id, String name) implements Serializable {

    public AuthorDTO(Author author) {
        this(
            author.getId(),
            author.getName()
        );
    }

}

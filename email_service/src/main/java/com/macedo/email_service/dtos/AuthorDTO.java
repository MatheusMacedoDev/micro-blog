package com.macedo.email_service.dtos;

import com.macedo.email_service.domain.entities.Author;

import java.io.Serializable;

public record AuthorDTO(int id, String name) implements Serializable {

    public AuthorDTO(Author author) {
        this(
            author.getId(),
            author.getName()
        );
    }

}

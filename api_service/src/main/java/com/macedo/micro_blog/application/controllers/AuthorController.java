package com.macedo.micro_blog.application.controllers;

import com.macedo.micro_blog.application.contracts.requests.CreateAuthorRequest;
import com.macedo.micro_blog.application.contracts.responses.AuthorDTO;
import com.macedo.micro_blog.domain.entities.Author;
import com.macedo.micro_blog.domain.repositories.AuthorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("authors")
public class AuthorController {

    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();

        List<AuthorDTO> mappedAuthors = authors.stream()
            .map(AuthorDTO::new)
            .toList();

        return ResponseEntity.ok(mappedAuthors);
    }

    @PostMapping
    public ResponseEntity<Void> createAuthor(@RequestBody CreateAuthorRequest request) {
        Author author = new Author(request);
        authorRepository.save(author);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}

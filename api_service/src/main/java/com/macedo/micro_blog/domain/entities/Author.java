package com.macedo.micro_blog.domain.entities;

import com.macedo.micro_blog.application.contracts.requests.CreateAuthorRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "authors")
@NoArgsConstructor
@Getter
public class Author implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @NotNull
    private String email;

    @OneToMany(mappedBy = "id")
    private List<Post> posts;

    public Author(CreateAuthorRequest request) {
        this.name = request.name();
        this.email = request.email();
    }

}

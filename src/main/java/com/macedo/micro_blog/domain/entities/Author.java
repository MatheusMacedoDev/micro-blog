package com.macedo.micro_blog.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "authors")
@NoArgsConstructor
public class Author {

    @Id
    private int id;

    private String name;

    @OneToMany(mappedBy = "id")
    private List<Post> posts;

}

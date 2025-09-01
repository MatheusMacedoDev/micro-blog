package com.macedo.micro_blog.domain.entities;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "posts")
@NoArgsConstructor
public class Post {

    @Id
    private int id;

    private String title;

    private String content;

    private Timestamp published_at;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

}

package com.macedo.micro_blog.domain.entities;

import com.macedo.micro_blog.application.contracts.requests.CreatePostRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "posts")
@NoArgsConstructor
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String content;

    private Timestamp publishedAt;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    public Post(CreatePostRequest request, Author author) {
        this.title = request.title();
        this.content = request.content();
        this.publishedAt = new Timestamp(System.currentTimeMillis());
        this.author = author;
    }

}

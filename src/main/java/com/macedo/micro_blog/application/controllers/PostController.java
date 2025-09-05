package com.macedo.micro_blog.application.controllers;

import com.macedo.micro_blog.application.contracts.requests.CreatePostRequest;
import com.macedo.micro_blog.application.contracts.responses.PostDTO;
import com.macedo.micro_blog.application.services.PostService;
import com.macedo.micro_blog.domain.entities.Author;
import com.macedo.micro_blog.domain.entities.Post;
import com.macedo.micro_blog.domain.repositories.AuthorRepository;
import com.macedo.micro_blog.domain.repositories.PostRepository;
import com.macedo.micro_blog.infra.rabbitmq.EmailDto;
import com.macedo.micro_blog.infra.rabbitmq.RabbitEmailPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("posts")
public class PostController {

    private final PostService postService;

    private final AuthorRepository authorRepository;
    private final PostRepository postRepository;

    public PostController(PostService postService, AuthorRepository authorRepository, PostRepository postRepository) {
        this.postService = postService;
        this.authorRepository = authorRepository;
        this.postRepository = postRepository;
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<Post> posts = postRepository.findAll();

        List<PostDTO> mappedPosts = posts.stream()
                .map(PostDTO::new)
                .toList();

        return ResponseEntity.ok(mappedPosts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable int id) {
        try {
            PostDTO post = postService.getPostById(id);
            return ResponseEntity.ok(post);
        } catch (Exception exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody CreatePostRequest request) {
        try {
            Author author = authorRepository.getReferenceById(request.authorId());
            Post post = new Post(request, author);

            postRepository.save(post);

            return ResponseEntity.ok().build();
        } catch (RuntimeException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/send-email")
    public ResponseEntity<Void> sendEmail(@RequestBody EmailDto emailDto) {
        postService.publishEmail(emailDto);
        return ResponseEntity.ok().build();
    }

}

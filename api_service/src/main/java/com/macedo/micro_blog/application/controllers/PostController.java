package com.macedo.micro_blog.application.controllers;

import com.macedo.micro_blog.application.contracts.requests.CreatePostRequest;
import com.macedo.micro_blog.application.contracts.responses.PostDTO;
import com.macedo.micro_blog.application.services.PostService;
import com.macedo.micro_blog.domain.entities.Author;
import com.macedo.micro_blog.domain.entities.Post;
import com.macedo.micro_blog.domain.repositories.AuthorRepository;
import com.macedo.micro_blog.domain.repositories.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("posts")
@Slf4j
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
            PostDTO post = postService.getPostByIdHandler(id);

            return ResponseEntity.ok(post);
        } catch (Exception exception) {
            log.error(exception.toString());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody CreatePostRequest request) {
        try {
            Author author = authorRepository.getReferenceById(request.authorId());
            Post post = new Post(request, author);

            postRepository.save(post);

            PostDTO postDTO = new PostDTO(post);
            postService.publishEmail(postDTO);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(post.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (RuntimeException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

}

package com.macedo.micro_blog.application.controllers;

import com.macedo.micro_blog.application.contracts.requests.CreatePostRequest;
import com.macedo.micro_blog.application.contracts.responses.PostDTO;
import com.macedo.micro_blog.domain.entities.Author;
import com.macedo.micro_blog.domain.entities.Post;
import com.macedo.micro_blog.domain.repositories.AuthorRepository;
import com.macedo.micro_blog.domain.repositories.PostRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("posts")
public class PostController {

    private final AuthorRepository authorRepository;
    private final PostRepository postRepository;

    public PostController(AuthorRepository authorRepository, PostRepository postRepository) {
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
            Optional<Post> postOptional = postRepository.findById(id);

            if (postOptional.isEmpty())
                throw new RuntimeException("There is no post with this id.");

            PostDTO mappedPost = new PostDTO(postOptional.get());

            return ResponseEntity.ok(mappedPost);

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

}

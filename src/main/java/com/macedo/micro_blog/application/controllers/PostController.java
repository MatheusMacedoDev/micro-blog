package com.macedo.micro_blog.application.controllers;

import com.macedo.micro_blog.application.contracts.requests.CreatePostRequest;
import com.macedo.micro_blog.application.contracts.responses.PostDTO;
import com.macedo.micro_blog.application.services.PostService;
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
    public ResponseEntity<Void> sendEmail() {
        Optional<Author> authorOptional = authorRepository.findById(1);
        Author author = authorOptional.get();

        Post post = new Post(
            new CreatePostRequest(author.getId(), "Um título qualquer", "Quisque in molestie dolor. Maecenas vestibulum, dolor quis ornare molestie, nunc tellus volutpat sem, nec volutpat diam leo non odio. Pellentesque blandit metus et nisl hendrerit condimentum. Maecenas id velit ut leo suscipit posuere. Phasellus eu ante vel lacus hendrerit viverra. Aenean aliquet leo vel nulla dapibus placerat. Maecenas felis ipsum, ultricies et venenatis eget, pulvinar vitae dolor. Proin at quam bibendum, consectetur mauris in, pretium ex. Curabitur pharetra nunc ut mi maximus, vitae rutrum metus lacinia. Quisque a lorem nulla. Vestibulum commodo euismod nulla quis auctor. Nulla feugiat ligula ligula, vel congue justo rhoncus quis. In vel urna pharetra, vestibulum nisl sit amet, condimentum urna. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Cras maximus quam eget accumsan faucibus. Pellentesque magna dui, facilisis a est sed, eleifend pulvinar nibh. "),
            author
        );

        PostDTO postDTO = new PostDTO(post);

        postService.publishEmail(postDTO);

        return ResponseEntity.ok().build();
    }

}

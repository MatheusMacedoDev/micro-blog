package com.macedo.micro_blog.application.services;

import com.macedo.micro_blog.application.contracts.responses.PostDTO;
import com.macedo.micro_blog.domain.entities.Post;
import com.macedo.micro_blog.domain.repositories.PostRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Cacheable(value = "posts", key = "#id")
    public PostDTO getPostById(int id) {
        Optional<Post> postOptional = postRepository.findById(id);

        if (postOptional.isEmpty())
            throw new RuntimeException("There is no post with this id.");

        return new PostDTO(postOptional.get());
    }
}

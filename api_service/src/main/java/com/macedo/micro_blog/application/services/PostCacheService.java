package com.macedo.micro_blog.application.services;

import com.macedo.micro_blog.domain.entities.Post;
import com.macedo.micro_blog.domain.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostCacheService {

    private final PostRepository postRepository;

    @Cacheable(value = "posts", key = "#id")
    public Post getPostById(int id) {
        Optional<Post> postOptional = postRepository.findById(id);

        if (postOptional.isEmpty())
            throw new RuntimeException("There is no post with this id.");

        return postOptional.get();
    }
}
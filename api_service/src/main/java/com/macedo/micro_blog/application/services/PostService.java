package com.macedo.micro_blog.application.services;

import com.macedo.micro_blog.application.contracts.responses.PostDTO;
import com.macedo.micro_blog.domain.entities.Post;
import com.macedo.micro_blog.domain.repositories.PostRepository;
import com.macedo.micro_blog.infra.rabbitmq.RabbitEmailPublisher;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final RabbitEmailPublisher emailPublisher;

    private final PostRepository postRepository;

    private final PostCacheService postCacheService;

    @Transactional
    public PostDTO getPostByIdHandler(int id) {
        PostDTO post = postCacheService.getPostById(id);

        postRepository.addViewToPost(post.id());

        return post;
    }

    public void publishEmail(PostDTO post) {
        emailPublisher.publish(post);
    }
}
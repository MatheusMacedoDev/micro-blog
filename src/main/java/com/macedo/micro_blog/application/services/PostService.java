package com.macedo.micro_blog.application.services;

import com.macedo.micro_blog.application.contracts.responses.PostDTO;
import com.macedo.micro_blog.domain.entities.Post;
import com.macedo.micro_blog.domain.repositories.PostRepository;
import com.macedo.micro_blog.infra.rabbitmq.RabbitEmailPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final RabbitEmailPublisher emailPublisher;

    private final PostRepository postRepository;

    @Cacheable(value = "posts", key = "#id")
    public PostDTO getPostById(int id) {
        Optional<Post> postOptional = postRepository.findById(id);

        if (postOptional.isEmpty())
            throw new RuntimeException("There is no post with this id.");

        return new PostDTO(postOptional.get());
    }

    public void publishEmail(Post post) {
        emailPublisher.publish(post);
    }
}

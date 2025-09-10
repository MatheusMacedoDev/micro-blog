package com.macedo.micro_blog.infra.rabbitmq;

public interface MessagePublisher {
    void publish(Object message);
}

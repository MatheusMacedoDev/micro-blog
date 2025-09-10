package com.macedo.micro_blog.application.contracts.requests;

public record CreatePostRequest(int authorId, String title, String content) {
}

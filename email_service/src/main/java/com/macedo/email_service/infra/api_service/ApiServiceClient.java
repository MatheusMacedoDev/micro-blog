package com.macedo.email_service.infra.api_service;

import com.macedo.email_service.dtos.AuthorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "micro-blog", url = "http://localhost:8080/", path = "/authors")
public interface ApiServiceClient {

    @GetMapping
    List<AuthorDTO> getAllAuthors();

}

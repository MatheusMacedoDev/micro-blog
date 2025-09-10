package com.macedo.micro_blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MicroBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroBlogApplication.class, args);
	}

}

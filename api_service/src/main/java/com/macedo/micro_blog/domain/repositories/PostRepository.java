package com.macedo.micro_blog.domain.repositories;

import com.macedo.micro_blog.domain.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Modifying
    @Query(value = "UPDATE posts SET views = views + 1 WHERE id = :postId", nativeQuery = true)
    void addViewToPost(@Param("postId") int postId);

}

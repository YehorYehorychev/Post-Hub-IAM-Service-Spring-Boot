package com.post_hub.iam_service.repositories;

import com.post_hub.iam_service.model.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {

    boolean existsByTitle(@Param("title") String title);

    boolean existsByContent(@Param("content") String content);

    Optional<Post> findByIdAndDeletedFalse(Integer id);
}
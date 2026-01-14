package com.post_hub.iam_service.repositories;

import com.post_hub.iam_service.model.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer>, JpaSpecificationExecutor<Post> {

    boolean existsByTitle(@Param("title") String title);

    boolean existsByContent(@Param("content") String content);

    Optional<Post> findByIdAndDeletedFalse(Integer id);

    Page<Post> findAllByDeletedFalse(Pageable pageable);
}
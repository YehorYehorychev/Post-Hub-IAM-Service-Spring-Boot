package com.post_hub.iam_service.repositories;

import com.post_hub.iam_service.model.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {

}

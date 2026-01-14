package com.post_hub.iam_service.service;

import com.post_hub.iam_service.model.dto.post.PostDto;
import com.post_hub.iam_service.model.dto.post.PostSearchDto;
import com.post_hub.iam_service.model.request.post.PostRequest;
import com.post_hub.iam_service.model.request.post.PostSearchRequest;
import com.post_hub.iam_service.model.request.post.UpdatePostRequest;
import com.post_hub.iam_service.model.response.IamResponse;
import com.post_hub.iam_service.model.response.PaginationResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;

public interface PostService {

    IamResponse<PostDto> getPostById(@NotNull Integer id);

    IamResponse<PostDto> createPost(@NotNull PostRequest postRequest);

    IamResponse<PostDto> updatePost(@NotNull Integer id, @NotNull UpdatePostRequest updatePostRequest);

    void softDeletePost(@NotNull Integer id);

    IamResponse<PaginationResponse<PostSearchDto>> findAllPosts(Pageable pageable);

    IamResponse<PaginationResponse<PostSearchDto>> searchPosts(@NotNull PostSearchRequest request, Pageable pageable);
}
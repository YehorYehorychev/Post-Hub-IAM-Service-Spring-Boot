package com.post_hub.iam_service.service;

import com.post_hub.iam_service.model.dto.post.PostDto;
import com.post_hub.iam_service.model.request.post.PostRequest;
import com.post_hub.iam_service.model.request.post.UpdatePostRequest;
import com.post_hub.iam_service.model.response.IamResponse;
import jakarta.validation.constraints.NotNull;

public interface PostService {

    IamResponse<PostDto> getPostById(@NotNull Integer id);

    IamResponse<PostDto> createPost(@NotNull PostRequest postRequest);

    IamResponse<PostDto> updatePost(@NotNull Integer id, @NotNull UpdatePostRequest updatePostRequest);
}
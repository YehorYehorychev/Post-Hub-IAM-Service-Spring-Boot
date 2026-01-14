package com.post_hub.iam_service.service.impl;

import com.post_hub.iam_service.mapper.PostMapper;
import com.post_hub.iam_service.model.constans.ApiErrorMessage;
import com.post_hub.iam_service.model.dto.post.PostDto;
import com.post_hub.iam_service.model.entities.Post;
import com.post_hub.iam_service.model.exception.DataExistException;
import com.post_hub.iam_service.model.exception.NotFoundException;
import com.post_hub.iam_service.model.request.post.PostRequest;
import com.post_hub.iam_service.model.request.post.UpdatePostRequest;
import com.post_hub.iam_service.model.response.IamResponse;
import com.post_hub.iam_service.repositories.PostRepository;
import com.post_hub.iam_service.service.PostService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Override
    public IamResponse<PostDto> getPostById(@NotNull Integer id) {
        Post post = postRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.POST_NOT_FOUND_BY_ID.getMessage(id)));

        PostDto postDto = postMapper.toPostDto(post);

        return IamResponse.createdSuccessfully(postDto);
    }

    @Override
    public IamResponse<PostDto> createPost(@NotNull PostRequest postRequest) {
        if (postRepository.existsByTitle(postRequest.getTitle())) {
            throw new DataExistException(ApiErrorMessage.POST_ALREADY_EXISTS_BY_TITLE.getMessage(postRequest.getTitle()));
        } else if (postRepository.existsByContent(postRequest.getContent())) {
            throw new DataExistException(ApiErrorMessage.POST_ALREADY_EXISTS_BY_CONTENT.getMessage(postRequest.getContent()));
        }

        Post post = postMapper.createPost(postRequest);

        Post savedPost = postRepository.save(post);
        PostDto postDto = postMapper.toPostDto(savedPost);

        return IamResponse.createdSuccessfully(postDto);
    }

    @Override
    public IamResponse<PostDto> updatePost(@NotNull Integer id, @NotNull UpdatePostRequest updatePostRequest) {
        Post post = postRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.POST_NOT_FOUND_BY_ID.getMessage(id)));

        postMapper.updatePost(post, updatePostRequest);
        post.setUpdated(LocalDateTime.now());
        post = postRepository.save(post);

        PostDto postDto = postMapper.toPostDto(post);
        return IamResponse.createdSuccessfully(postDto);
    }

    @Override
    public void softDeletePost(Integer id) {
        Post post = postRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.POST_NOT_FOUND_BY_ID.getMessage(id)));

        post.setDeleted(true);
        postRepository.save(post);
    }
}

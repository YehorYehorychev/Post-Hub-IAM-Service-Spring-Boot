package com.post_hub.iam_service.service.impl;

import com.post_hub.iam_service.mapper.PostMapper;
import com.post_hub.iam_service.model.constans.ApiErrorMessage;
import com.post_hub.iam_service.model.dto.post.PostDto;
import com.post_hub.iam_service.model.dto.post.PostSearchDto;
import com.post_hub.iam_service.model.entities.Post;
import com.post_hub.iam_service.model.entities.User;
import com.post_hub.iam_service.model.exception.DataExistException;
import com.post_hub.iam_service.model.exception.NotFoundException;
import com.post_hub.iam_service.model.request.post.PostRequest;
import com.post_hub.iam_service.model.request.post.PostSearchRequest;
import com.post_hub.iam_service.model.request.post.UpdatePostRequest;
import com.post_hub.iam_service.model.response.IamResponse;
import com.post_hub.iam_service.model.response.PaginationResponse;
import com.post_hub.iam_service.repositories.PostRepository;
import com.post_hub.iam_service.repositories.UserRepository;
import com.post_hub.iam_service.repositories.criteria.PostSearchCriteria;
import com.post_hub.iam_service.service.PostService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserRepository userRepository;

    @Override
    public IamResponse<PostDto> getPostById(@NotNull Integer id) {
        Post post = postRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.POST_NOT_FOUND_BY_ID.getMessage(id)));

        PostDto postDto = postMapper.toPostDto(post);

        return IamResponse.createdSuccessfully(postDto);
    }

    @Override
    public IamResponse<PostDto> createPost(@NotNull Integer userId, PostRequest postRequest) {
        if (postRepository.existsByTitle(postRequest.getTitle())) {
            throw new DataExistException(ApiErrorMessage.POST_ALREADY_EXISTS_BY_TITLE.getMessage(postRequest.getTitle()));
        } else if (postRepository.existsByContent(postRequest.getContent())) {
            throw new DataExistException(ApiErrorMessage.POST_ALREADY_EXISTS_BY_CONTENT.getMessage(postRequest.getContent()));
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.USER_NOT_FOUND_BY_ID.getMessage(userId)));

        Post post = postMapper.createPost(postRequest, user);
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

    @Override
    public IamResponse<PaginationResponse<PostSearchDto>> findAllPosts(Pageable pageable) {
        Page<PostSearchDto> posts = postRepository.findAllByDeletedFalse(pageable)
                .map(postMapper::toPostSearchDto);

        PaginationResponse<PostSearchDto> paginationResponse = new PaginationResponse<>(
                posts.getContent(),
                new PaginationResponse.Pagination(
                        posts.getTotalElements(),
                        pageable.getPageSize(),
                        posts.getNumber() + 1,
                        posts.getTotalPages()
                )
        );

        return IamResponse.createdSuccessfully(paginationResponse);
    }

    @Override
    public IamResponse<PaginationResponse<PostSearchDto>> searchPosts(PostSearchRequest request, Pageable pageable) {
        Specification<Post> specification = new PostSearchCriteria(request);
        Page<PostSearchDto> posts = postRepository.findAll(specification, pageable)
                .map(postMapper::toPostSearchDto);

        PaginationResponse<PostSearchDto> response = PaginationResponse.<PostSearchDto>builder()
                .content(posts.getContent())
                .pagination(PaginationResponse.Pagination.builder()
                        .total(posts.getTotalElements())
                        .limit(pageable.getPageSize())
                        .page(posts.getNumber() + 1)
                        .pages(posts.getTotalPages())
                        .build())
                .build();

        return IamResponse.createdSuccessfully(response);
    }
}

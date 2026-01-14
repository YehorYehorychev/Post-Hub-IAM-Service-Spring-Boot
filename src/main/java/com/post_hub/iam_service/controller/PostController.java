package com.post_hub.iam_service.controller;

import com.post_hub.iam_service.model.constans.ApiLogMessage;
import com.post_hub.iam_service.model.dto.post.PostDto;
import com.post_hub.iam_service.model.dto.post.PostSearchDto;
import com.post_hub.iam_service.model.request.post.PostRequest;
import com.post_hub.iam_service.model.request.post.UpdatePostRequest;
import com.post_hub.iam_service.model.response.IamResponse;
import com.post_hub.iam_service.model.response.PaginationResponse;
import com.post_hub.iam_service.service.PostService;
import com.post_hub.iam_service.utils.ApiUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("${endpoint.posts}")
public class PostController {

    private final PostService postService;

    @GetMapping("${endpoint.id}")
    public ResponseEntity<IamResponse<PostDto>> getPostById(@PathVariable(name = "id") Integer id) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        IamResponse<PostDto> response = postService.getPostById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("${endpoint.create}")
    public ResponseEntity<IamResponse<PostDto>> createPost(@RequestBody @Valid PostRequest postRequest) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        IamResponse<PostDto> response = postService.createPost(postRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("${endpoint.id}")
    public ResponseEntity<IamResponse<PostDto>> updatePostById(@PathVariable(name = "id") Integer id,
                                                               @RequestBody @Valid UpdatePostRequest updatePostRequest) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        IamResponse<PostDto> response = postService.updatePost(id, updatePostRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("${endpoint.id}")
    public ResponseEntity<Void> softDeletePostById(@PathVariable(name = "id") Integer id) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        postService.softDeletePost(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("${endpoint.all}")
    public ResponseEntity<IamResponse<PaginationResponse<PostSearchDto>>> getAllPosts(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        Pageable pageable = PageRequest.of(page, limit);
        IamResponse<PaginationResponse<PostSearchDto>> response = postService.findAllPosts(pageable);
        return ResponseEntity.ok(response);
    }
}
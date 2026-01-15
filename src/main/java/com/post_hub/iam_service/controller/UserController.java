package com.post_hub.iam_service.controller;

import com.post_hub.iam_service.model.constans.ApiLogMessage;
import com.post_hub.iam_service.model.dto.user.UserDto;
import com.post_hub.iam_service.model.request.user.NewUserRequest;
import com.post_hub.iam_service.model.response.IamResponse;
import com.post_hub.iam_service.service.UserService;
import com.post_hub.iam_service.utils.ApiUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("${endpoint.users}")
public class UserController {

    private final UserService userService;

    @GetMapping("${endpoint.id}")
    public ResponseEntity<IamResponse<UserDto>> getUserById(
            @PathVariable(name = "id") Integer id) {
        log.info(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        IamResponse<UserDto> response = userService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("${endpoint.create}")
    public ResponseEntity<IamResponse<UserDto>> createUser(
            @RequestBody @Valid NewUserRequest request) {

        log.trace(
                ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(),
                ApiUtils.getMethodName()
        );

        IamResponse<UserDto> createdUser = userService.createUser(request);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("${endpoint.id}")
    public ResponseEntity<IamResponse<UserDto>> updateUserById(
            @PathVariable(name = "id") Integer userId,
            @RequestBody @Valid UpdateUserRequest request) {

        log.trace(
                ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(),
                ApiUtils.getMethodName()
        );

        IamResponse<UserDto> updatedPost = userService.updateUser(userId, request);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("${endpoint.id}")
    public ResponseEntity<Void> softDeleteUser(
            @PathVariable(name = "id") Integer id) {

        log.trace(
                ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(),
                ApiUtils.getMethodName()
        );

        userService.softDeleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("${endpoint.all}")
    public ResponseEntity<IamResponse<PaginationResponse<UserSearchDto>>> getAllUsers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {

        log.trace(
                ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(),
                ApiUtils.getMethodName()
        );

        Pageable pageable = PageRequest.of(page, limit);
        IamResponse<PaginationResponse<UserSearchDto>> response =
                userService.findAllUsers(pageable);

        return ResponseEntity.ok(response);
    }

    @PostMapping("${endpoint.search}")
    public ResponseEntity<IamResponse<PaginationResponse<UserSearchDto>>> searchUsers(
            @RequestBody @Valid UserSearchRequest request,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {

        log.trace(
                ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(),
                ApiUtils.getMethodName()
        );

        Pageable pageable = PageRequest.of(page, limit);
        IamResponse<PaginationResponse<UserSearchDto>> response =
                userService.searchUsers(request, pageable);

        return ResponseEntity.ok(response);
    }
}

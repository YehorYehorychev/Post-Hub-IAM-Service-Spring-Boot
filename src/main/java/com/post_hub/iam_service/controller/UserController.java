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
        log.info(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        IamResponse<UserDto> response = userService.createUser(request);
        return ResponseEntity.ok(response);
    }
}

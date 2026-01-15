package com.post_hub.iam_service.service;

import com.post_hub.iam_service.model.dto.user.UserDto;
import com.post_hub.iam_service.model.request.user.NewUserRequest;
import com.post_hub.iam_service.model.response.IamResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;

public interface UserService {

    IamResponse<UserDto> getById(@NotNull Integer id);

    IamResponse<UserDto> createUser(@NotNull NewUserRequest newUserRequest);

    IamResponse<UserDto> updateUser(@NotNull Integer postId, @NotNull UpdateUserRequest request);

    void softDeleteUser(Integer id);

    IamResponse<UserSearchDto> findAllUsers(Pageable pageable);

    IamResponse<UserSearchDto> searchUsers(UserSearchRequest request, Pageable pageable);
}

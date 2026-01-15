package com.post_hub.iam_service.model.constans;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ApiErrorMessage {
    POST_NOT_FOUND_BY_ID("Post with ID %d not found."),
    POST_ALREADY_EXISTS_BY_TITLE("Post with title '%s' already exists."),
    POST_ALREADY_EXISTS_BY_CONTENT("Post with content '%s' already exists."),
    USER_NOT_FOUND_BY_ID("User with ID %d not found."),
    USERNAME_ALREADY_EXISTS("Username '%s' already exists."),
    EMAIL_ALREADY_EXISTS("Email '%s' already exists.");

    private final String message;

    public String getMessage(Object... args) {
        return String.format(message, args);
    }
}

package com.post_hub.iam_service.controller;

import com.post_hub.iam_service.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService defaultCommentService;
    private final CommentService advancedCommentService;

    @Autowired
    public CommentController(@Qualifier("basicCommentService") CommentService defaultCommentService, CommentService advancedCommentService) {
        this.defaultCommentService = defaultCommentService;
        this.advancedCommentService = advancedCommentService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> addDefaultComment(@RequestBody Map<String, Object> requestBody) {
        String content = (String) requestBody.get("content");
        defaultCommentService.createComment(content);

        return new ResponseEntity<>("Default Comment: '" + content + "' - successfully added!", HttpStatus.OK);
    }

    @PostMapping("/createAdvanced")
    public ResponseEntity<String> addAdvancedComment(@RequestBody Map<String, Object> requestBody) {
        String content = (String) requestBody.get("content");
        advancedCommentService.createComment(content);

        return new ResponseEntity<>("[Comment service - version 2.0]\nComment: '" + content + "' - successfully added!", HttpStatus.OK);
    }
}
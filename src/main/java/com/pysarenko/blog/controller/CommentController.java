package com.pysarenko.blog.controller;

import com.pysarenko.blog.dto.CommentDto;
import com.pysarenko.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts/{post-id}/comments")
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  CommentDto createComment(@PathVariable("post-id") String postId, @RequestBody CommentDto comment) {
    return commentService.createComment(postId, comment);
  }

  @PatchMapping("/{comment-id}")
  CommentDto updateComment(@PathVariable("post-id") String postId,
      @PathVariable("comment-id") String commentId, @RequestBody CommentDto comment) {
    return commentService.updateComment(postId, commentId, comment);
  }

  @GetMapping
  Page<CommentDto> getAllPostComments(@PathVariable("post-id") String postId, @PageableDefault Pageable pageable) {
    return commentService.getAllPostComments(postId, pageable);
  }

  @DeleteMapping("/{comment-id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void deleteComment(@PathVariable("post-id") String postId, @PathVariable("comment-id") String id) {
    commentService.deleteComment(postId, id);
  }
}

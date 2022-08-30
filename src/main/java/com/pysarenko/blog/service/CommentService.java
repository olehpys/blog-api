package com.pysarenko.blog.service;

import com.pysarenko.blog.dto.CommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

  CommentDto createComment(String postId, CommentDto comment);

  CommentDto updateComment(String postId, String commentId, CommentDto comment);

  Page<CommentDto> getAllPostComments(String postId, Pageable pageable);

  void deleteComment(String postId, String id);
}

package com.pysarenko.blog.service.impl;

import static com.pysarenko.blog.security.utils.BlogSecurityUtils.getUsernameFromSecurityContext;
import static com.pysarenko.blog.security.utils.BlogSecurityUtils.isAdmin;

import com.pysarenko.blog.dto.CommentDto;
import com.pysarenko.blog.mapper.CommentMapper;
import com.pysarenko.blog.entity.Comment;
import com.pysarenko.blog.repository.CommentRepository;
import com.pysarenko.blog.repository.PostRepository;
import com.pysarenko.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final PostRepository postRepository;
  private final CommentRepository commentRepository;
  private final CommentMapper commentMapper;

  @Override
  @Transactional
  public CommentDto createComment(String postId, CommentDto dto) {
    var post = postRepository.findById(postId)
        .orElseThrow();

    var comment = commentMapper.toComment(dto);
    comment.setAuthorUsername(getUsernameFromSecurityContext());
    comment.setPost(post);

    var savedComment = commentRepository.save(comment);
    log.info("Created comment with id: {} for post: {} by user: {}",
        savedComment.getId(), postId, getUsernameFromSecurityContext());
    return commentMapper.toDto(savedComment);
  }

  @Override
  @Transactional
  public CommentDto updateComment(String postId, String commentId, CommentDto comment) {
    postRepository.findById(postId)
        .orElseThrow();

    var storedComment = commentRepository.findById(commentId)
        .filter(this::userHasPermission)
        .orElseThrow();

    var commentToUpdate = commentMapper.updateComment(storedComment, comment);
    var updatedComment = commentRepository.save(commentToUpdate);

    log.info("Updated comment with id: {} for post: {} by user: {}",
        commentId, postId, getUsernameFromSecurityContext());

    return commentMapper.toDto(updatedComment);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<CommentDto> getAllPostComments(String postId, Pageable pageable) {
    log.info("Retrieving all comments for post with id: {}", postId);
    return commentRepository.findAllByPostId(postId, pageable)
        .map(commentMapper::toDto);
  }

  @Override
  @Transactional
  public void deleteComment(String postId, String id) {
    postRepository.findById(postId)
        .orElseThrow();

    commentRepository.findById(id)
        .filter(this::userHasPermission)
        .ifPresent(c -> {
          commentRepository.deleteById(id);
          log.info("Deleted comment with id: {} by user: {}", c.getId(), getUsernameFromSecurityContext());
        });
  }

  private boolean userHasPermission(Comment comment) {
    return comment.getAuthorUsername().equals(getUsernameFromSecurityContext()) || isAdmin();
  }
}

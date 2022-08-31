package com.pysarenko.blog.service;

import static com.pysarenko.blog.TestUtils.TEST_CONTENT;
import static com.pysarenko.blog.TestUtils.TEST_TITLE;
import static com.pysarenko.blog.TestUtils.TEST_USERNAME;
import static com.pysarenko.blog.TestUtils.buildComment;
import static com.pysarenko.blog.TestUtils.buildCommentDto;
import static com.pysarenko.blog.TestUtils.buildPost;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.pysarenko.blog.dto.CommentDto;
import com.pysarenko.blog.entity.Comment;
import com.pysarenko.blog.mapper.CommentMapper;
import com.pysarenko.blog.mapper.impl.CommentMapperImpl;
import com.pysarenko.blog.repository.CommentRepository;
import com.pysarenko.blog.repository.PostRepository;
import com.pysarenko.blog.security.model.UserProfile;
import com.pysarenko.blog.security.utils.BlogSecurityUtils;
import com.pysarenko.blog.service.impl.CommentServiceImpl;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTest {

  @Mock
  private PostRepository postRepository;

  @Mock
  private CommentRepository commentRepository;

  @Spy
  private CommentMapper commentMapper = new CommentMapperImpl();

  @InjectMocks
  private CommentServiceImpl commentService;

  private static MockedStatic<BlogSecurityUtils> blogSecurityUtils;

  @BeforeAll
  public static void beforeAll() {
    blogSecurityUtils = Mockito.mockStatic(BlogSecurityUtils.class);
    UserProfile userProfile = mock(UserProfile.class);
    blogSecurityUtils.when(BlogSecurityUtils::getUserProfileFromSecurityContext)
        .thenReturn(userProfile);
    blogSecurityUtils.when(BlogSecurityUtils::getUsernameFromSecurityContext)
        .thenReturn(TEST_USERNAME);
  }

  @AfterAll
  static void afterAll() {
    blogSecurityUtils.close();
  }

  @Test
  void shouldCreateComment() {
    var postId = UUID.randomUUID().toString();
    var commentId = UUID.randomUUID().toString();
    var expectedComment = buildCommentDto(commentId, TEST_CONTENT, TEST_USERNAME);
    var request = buildCommentDto(TEST_CONTENT);
    var post = buildPost(postId, TEST_TITLE, TEST_CONTENT, TEST_USERNAME);
    var savedComment = buildComment(commentId, TEST_CONTENT, TEST_USERNAME, post);
    when(postRepository.findById(eq(postId))).thenReturn(Optional.of(post));
    when(commentRepository.save(any(Comment.class))).thenReturn(savedComment);

    var createdComment = commentService.createComment(postId, request);

    assertThat(createdComment).isEqualTo(expectedComment);
    verify(commentMapper).toDto(any(Comment.class));
    verify(commentMapper).toComment(any(CommentDto.class));
  }

  @Test
  void shouldUpdateComment() {
    var postId = UUID.randomUUID().toString();
    var commentId = UUID.randomUUID().toString();
    var expectedComment = buildCommentDto(commentId, TEST_CONTENT, TEST_USERNAME);
    var request = buildCommentDto(TEST_CONTENT);
    var post = buildPost(postId, TEST_TITLE, TEST_CONTENT, TEST_USERNAME);
    var savedComment = buildComment(commentId, TEST_CONTENT, TEST_USERNAME, post);
    when(postRepository.findById(eq(postId))).thenReturn(Optional.of(post));
    when(commentRepository.findById(eq(commentId))).thenReturn(Optional.of(savedComment));
    when(commentRepository.save(any(Comment.class))).thenReturn(savedComment);

    var updatedComment = commentService.updateComment(postId, commentId, request);

    assertThat(updatedComment).isEqualTo(expectedComment);
    verify(commentMapper).toDto(any(Comment.class));
    verify(commentMapper).updateComment(any(Comment.class), any(CommentDto.class));
  }

  @Test
  void shouldGetAllPostComments() {
    var postId = UUID.randomUUID().toString();
    var commentId = UUID.randomUUID().toString();
    var expectedComment = buildCommentDto(commentId, TEST_CONTENT, TEST_USERNAME);
    var post = buildPost(postId, TEST_TITLE, TEST_CONTENT, TEST_USERNAME);
    var comment = buildComment(commentId, TEST_CONTENT, TEST_USERNAME, post);
    when(commentRepository.findAllByPostId(eq(postId), any(Pageable.class)))
        .thenReturn(new PageImpl<>(singletonList(comment)));

    var postComments = commentService.getAllPostComments(postId, Pageable.ofSize(10));

    assertThat(postComments.getContent()).containsExactly(expectedComment);
    verify(commentMapper).toDto(eq(comment));
    verifyNoMoreInteractions(commentRepository);
  }

  @Test
  void shouldDeleteComment() {
    var postId = UUID.randomUUID().toString();
    var commentId = UUID.randomUUID().toString();
    var post = buildPost(postId, TEST_TITLE, TEST_CONTENT, TEST_USERNAME);
    var comment = buildComment(commentId, TEST_CONTENT, TEST_USERNAME, post);
    when(postRepository.findById(eq(postId))).thenReturn(Optional.of(post));
    when(commentRepository.findById(eq(commentId))).thenReturn(Optional.of(comment));
    doNothing().when(commentRepository).deleteById(commentId);

    commentService.deleteComment(postId, commentId);

    verifyNoMoreInteractions(commentRepository);
    verifyNoMoreInteractions(postRepository);
    verifyNoInteractions(commentMapper);
  }
}

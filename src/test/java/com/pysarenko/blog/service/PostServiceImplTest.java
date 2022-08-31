package com.pysarenko.blog.service;

import static com.pysarenko.blog.TestUtils.TEST_CONTENT;
import static com.pysarenko.blog.TestUtils.TEST_TITLE;
import static com.pysarenko.blog.TestUtils.TEST_USERNAME;
import static com.pysarenko.blog.TestUtils.buildPost;
import static com.pysarenko.blog.TestUtils.buildPostDto;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.pysarenko.blog.dto.PostDto;
import com.pysarenko.blog.entity.Post;
import com.pysarenko.blog.mapper.PostMapper;
import com.pysarenko.blog.mapper.impl.PostMapperImpl;
import com.pysarenko.blog.repository.PostRepository;
import com.pysarenko.blog.security.model.UserProfile;
import com.pysarenko.blog.security.model.UserRole;
import com.pysarenko.blog.security.utils.BlogSecurityUtils;
import com.pysarenko.blog.service.impl.PostServiceImpl;
import java.util.NoSuchElementException;
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
public class PostServiceImplTest {

  @Mock
  private PostRepository postRepository;

  @Spy
  private PostMapper postMapper = new PostMapperImpl();

  @InjectMocks
  private PostServiceImpl postService;

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
  void shouldCreatePost() {
    var postId = UUID.randomUUID().toString();
    var expectedPost = buildPostDto(postId, TEST_TITLE, TEST_CONTENT, TEST_USERNAME);
    when(postRepository.save(any(Post.class))).thenReturn(buildPost(postId, TEST_TITLE, TEST_CONTENT, TEST_USERNAME));

    var createdPost = postService.createPost(buildPostDto(TEST_TITLE, TEST_CONTENT));

    assertThat(createdPost).isEqualTo(expectedPost);
    verify(postMapper).toDto(any(Post.class));
    verify(postMapper).toPost(any(PostDto.class));
  }

  @Test
  void shouldUpdatePost() {
    var postId = UUID.randomUUID().toString();
    var postToUpdate = buildPostDto(TEST_TITLE, TEST_CONTENT);
    var storedPost = buildPost(postId, TEST_TITLE, TEST_CONTENT, TEST_USERNAME);
    var expectedPostDto = buildPostDto(postId, TEST_TITLE, TEST_CONTENT, TEST_USERNAME);
    when(postRepository.findById(eq(postId))).thenReturn(Optional.of(storedPost));
    when(postRepository.save(any(Post.class))).thenReturn(buildPost(postId, TEST_TITLE, TEST_CONTENT, TEST_USERNAME));

    var updatedPostDto = postService.updatePost(postId, postToUpdate);

    assertThat(updatedPostDto).isEqualTo(expectedPostDto);
    verify(postMapper).toDto(any(Post.class));
    verify(postMapper).updatePost(eq(storedPost), eq(postToUpdate));
  }

  @Test
  void shouldNotUpdatePostWhenOriginalPostIsNotFound() {
    var postId = UUID.randomUUID().toString();
    when(postRepository.findById(eq(postId))).thenReturn(Optional.empty());

    assertThatThrownBy(() -> postService.updatePost(postId, buildPostDto(TEST_TITLE, TEST_CONTENT)))
        .isInstanceOf(NoSuchElementException.class);
    verifyNoMoreInteractions(postRepository);
    verifyNoInteractions(postMapper);
  }

  @Test
  void shouldDeletePost() {
    var postId = UUID.randomUUID().toString();
    var storedPost = buildPost(postId, TEST_TITLE, TEST_CONTENT, TEST_USERNAME);
    when(postRepository.findById(eq(postId))).thenReturn(Optional.of(storedPost));
    doNothing().when(postRepository).deleteById(eq(postId));

    postService.deletePost(postId);

    verifyNoMoreInteractions(postRepository);
    verifyNoInteractions(postMapper);
  }

  @Test
  void shouldGetPostById() {
    var postId = UUID.randomUUID().toString();
    var storedPost = buildPost(postId, TEST_TITLE, TEST_CONTENT, TEST_USERNAME);
    var expectedPost = buildPostDto(postId, TEST_TITLE, TEST_CONTENT, TEST_USERNAME);
    when(postRepository.findById(eq(postId))).thenReturn(Optional.of(storedPost));

    var actualPost = postService.getPost(postId);

    assertThat(actualPost).isEqualTo(expectedPost);
    verify(postMapper).toDto(eq(storedPost));
    verifyNoMoreInteractions(postRepository);
  }

  @Test
  void shouldGetAllPostsForPublisher() {
    var postId = UUID.randomUUID().toString();
    var storedPost = buildPost(postId, TEST_TITLE, TEST_CONTENT, TEST_USERNAME);
    var expectedPost = buildPostDto(postId, TEST_TITLE, TEST_CONTENT, TEST_USERNAME);
    blogSecurityUtils.when(BlogSecurityUtils::getUserRoleFromSecurityContext)
        .thenReturn(UserRole.PUBLISHER);
    when(postRepository.findAllByAuthorUsername(eq(TEST_USERNAME), any(Pageable.class)))
        .thenReturn(new PageImpl<>(singletonList(storedPost)));

    var allPosts = postService.getAllPosts(Pageable.ofSize(10));

    assertThat(allPosts.getContent()).containsExactly(expectedPost);
    verify(postMapper).toDto(eq(storedPost));
    verifyNoMoreInteractions(postRepository);
  }

  @Test
  void shouldGetAllPostsForAdmin() {
    var postId = UUID.randomUUID().toString();
    var storedPost = buildPost(postId, TEST_TITLE, TEST_CONTENT, TEST_USERNAME);
    var expectedPost = buildPostDto(postId, TEST_TITLE, TEST_CONTENT, TEST_USERNAME);
    blogSecurityUtils.when(BlogSecurityUtils::getUserRoleFromSecurityContext)
        .thenReturn(UserRole.ADMIN);
    when(postRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(singletonList(storedPost)));

    var allPosts = postService.getAllPosts(Pageable.ofSize(10));

    assertThat(allPosts.getContent()).containsExactly(expectedPost);
    verify(postMapper).toDto(eq(storedPost));
    verifyNoMoreInteractions(postRepository);
  }
}

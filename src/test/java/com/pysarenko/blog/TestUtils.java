package com.pysarenko.blog;

import com.pysarenko.blog.dto.CommentDto;
import com.pysarenko.blog.dto.PostDto;
import com.pysarenko.blog.entity.Comment;
import com.pysarenko.blog.entity.Post;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestUtils {

  public static final String TEST_USERNAME = "testUsername";
  public static final String TEST_TITLE = "Test title";
  public static final String TEST_CONTENT = "Test content";

  public static PostDto buildPostDto(String title, String content) {
    return PostDto.builder()
        .title(title)
        .content(content)
        .build();
  }

  public static PostDto buildPostDto(String id, String title, String content) {
    return PostDto.builder()
        .id(id)
        .title(title)
        .content(content)
        .build();
  }

  public static PostDto buildPostDto(String id, String title, String content, String authorUsername) {
    return PostDto.builder()
        .id(id)
        .title(title)
        .content(content)
        .authorUsername(authorUsername)
        .build();
  }

  public static Post buildPost(String id, String title, String content, String authorUsername) {
    return Post.builder()
        .id(id)
        .title(title)
        .content(content)
        .authorUsername(authorUsername)
        .build();
  }

  public static Comment buildComment(String id, String content, String authorUsername, Post post) {
    return Comment.builder()
        .id(id)
        .content(content)
        .authorUsername(authorUsername)
        .post(post)
        .build();
  }

  public static CommentDto buildCommentDto(String id, String content, String authorUsername) {
    return CommentDto.builder()
        .id(id)
        .content(content)
        .authorUsername(authorUsername)
        .build();
  }

  public static CommentDto buildCommentDto(String content) {
    return CommentDto.builder()
        .content(content)
        .build();
  }
}

package com.pysarenko.blog.controller;

import static com.pysarenko.blog.TestUtils.TEST_CONTENT;
import static com.pysarenko.blog.TestUtils.TEST_USERNAME;
import static com.pysarenko.blog.TestUtils.buildCommentDto;
import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pysarenko.blog.service.CommentService;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CommentController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CommentControllerWebMvcTest extends AbstractWebMvcTest {

  @MockBean
  private CommentService commentService;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void shouldCreateComment() throws Exception {
    var postId = UUID.randomUUID().toString();
    var request = buildCommentDto(TEST_CONTENT);
    var response = buildCommentDto(UUID.randomUUID().toString(), TEST_CONTENT, TEST_USERNAME);
    when(commentService.createComment(eq(postId), eq(request))).thenReturn(response);

    mockMvc.perform(post("/posts/{postId}/comments", postId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(content().json(objectMapper.writeValueAsString(response)));
  }

  @Test
  void shouldUpdateComment() throws Exception {
    var postId = UUID.randomUUID().toString();
    var commentId = UUID.randomUUID().toString();
    var request = buildCommentDto(TEST_CONTENT);
    var response = buildCommentDto(UUID.randomUUID().toString(), TEST_CONTENT, TEST_USERNAME);
    when(commentService.updateComment(eq(postId), eq(commentId), eq(request))).thenReturn(response);

    mockMvc.perform(patch("/posts/{postId}/comments/{comment-id}", postId, commentId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(response)));
  }

  @Test
  void shouldDeleteComment() throws Exception {
    var postId = UUID.randomUUID().toString();
    var commentId = UUID.randomUUID().toString();
    doNothing().when(commentService).deleteComment(eq(postId), eq(commentId));

    mockMvc.perform(delete("/posts/{postId}/comments/{comment-id}", postId, commentId))
        .andExpect(status().isNoContent());
  }

  @Test
  void shouldGetAllComments() throws Exception {
    var postId = UUID.randomUUID().toString();
    var commentId = UUID.randomUUID().toString();
    var response = new PageImpl<>(singletonList(buildCommentDto(commentId, TEST_CONTENT, TEST_USERNAME)));
    when(commentService.getAllPostComments(eq(postId), any(Pageable.class))).thenReturn(response);

    mockMvc.perform(get("/posts/{postId}/comments", postId))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(response)));
  }
}

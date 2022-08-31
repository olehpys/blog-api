package com.pysarenko.blog.controller;

import static com.pysarenko.blog.TestUtils.TEST_CONTENT;
import static com.pysarenko.blog.TestUtils.TEST_TITLE;
import static com.pysarenko.blog.TestUtils.TEST_USERNAME;
import static com.pysarenko.blog.TestUtils.buildPostDto;
import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pysarenko.blog.service.PostService;
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

@WebMvcTest(PostController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PostControllerWebMvcTest extends AbstractWebMvcTest {

  @MockBean
  private PostService postService;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void shouldCreatePost() throws Exception {
    var request = buildPostDto(TEST_TITLE, TEST_CONTENT);
    var response = buildPostDto(UUID.randomUUID().toString(), TEST_TITLE, TEST_CONTENT, TEST_USERNAME);
    when(postService.createPost(eq(request))).thenReturn(response);

    mockMvc.perform(post("/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(content().json(objectMapper.writeValueAsString(response)));
  }

  @Test
  void shouldUpdatePost() throws Exception {
    var postId = UUID.randomUUID().toString();
    var request = buildPostDto(TEST_TITLE, TEST_CONTENT);
    var response = buildPostDto(postId, TEST_TITLE, TEST_CONTENT, TEST_USERNAME);
    when(postService.updatePost(eq(postId), eq(request))).thenReturn(response);

    mockMvc.perform(put("/posts/{id}", postId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(response)));
  }

  @Test
  void shouldDeletePost() throws Exception {
    var postId = UUID.randomUUID().toString();
   doNothing().when(postService).deletePost(eq(postId));

    mockMvc.perform(delete("/posts/{id}", postId))
        .andExpect(status().isNoContent());
  }

  @Test
  void shouldGetPostById() throws Exception {
    var postId = UUID.randomUUID().toString();
    var response = buildPostDto(postId, TEST_TITLE, TEST_CONTENT, TEST_USERNAME);
    when(postService.getPost(eq(postId))).thenReturn(response);

    mockMvc.perform(get("/posts/{id}", postId))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(response)));
  }

  @Test
  void shouldGetAllPosts() throws Exception {
    var postId = UUID.randomUUID().toString();
    var response = new PageImpl<>(singletonList(buildPostDto(postId, TEST_TITLE, TEST_CONTENT, TEST_USERNAME)));
    when(postService.getAllPosts(any(Pageable.class))).thenReturn(response);

    mockMvc.perform(get("/posts"))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(response)));
  }
}

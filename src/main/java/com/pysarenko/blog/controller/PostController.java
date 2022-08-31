package com.pysarenko.blog.controller;

import com.pysarenko.blog.dto.PostDto;
import com.pysarenko.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/posts")
@RequiredArgsConstructor
public class PostController {

  private final PostService postService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  PostDto createPost(@RequestBody PostDto post) {
    return postService.createPost(post);
  }

  @PutMapping("/{id}")
  PostDto editPost(@PathVariable String id, @RequestBody PostDto post) {
    return postService.updatePost(id, post);
  }

  @GetMapping("/{id}")
  PostDto getPost(@PathVariable String id) {
    return postService.getPost(id);
  }

  @GetMapping
  Page<PostDto> getAllPosts(@PageableDefault(sort = "id", direction = Direction.ASC) Pageable pageable) {
    return postService.getAllPosts(pageable);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void deletePost(@PathVariable String id) {
    postService.deletePost(id);
  }
}

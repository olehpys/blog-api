package com.pysarenko.blog.controller;

import com.pysarenko.blog.dto.PostDto;
import com.pysarenko.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/posts")
@RequiredArgsConstructor
public class PostController {

  private final PostService postService;

  @PostMapping
  PostDto createPost(@RequestBody PostDto post) {
    return postService.createPost(post);
  }
}

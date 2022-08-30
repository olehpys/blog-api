package com.pysarenko.blog.service;

import com.pysarenko.blog.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

  PostDto createPost(PostDto post);

  PostDto editPost(String id, PostDto post);

  void deletePost(String id);

  PostDto getPost(String id);

  Page<PostDto> getAllPosts(Pageable pageable);
}

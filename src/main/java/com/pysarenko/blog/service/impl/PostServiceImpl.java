package com.pysarenko.blog.service.impl;

import com.pysarenko.blog.dto.PostDto;
import com.pysarenko.blog.mapper.PostMapper;
import com.pysarenko.blog.repository.PostRepository;
import com.pysarenko.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;
  private final PostMapper postMapper;

  @Override
  @Transactional
  public PostDto createPost(PostDto postDto) {
    var savedPost = postRepository.save(postMapper.toPost(postDto));

    log.info("Created new post with id: {} for user: {}", savedPost.getId(), savedPost.getAuthorUsername());
    return postMapper.toDto(savedPost);
  }
}

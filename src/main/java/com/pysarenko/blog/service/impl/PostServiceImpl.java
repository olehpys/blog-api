package com.pysarenko.blog.service.impl;

import static com.pysarenko.blog.security.utils.BlogSecurityUtils.getUserProfileFromSecurityContext;
import static com.pysarenko.blog.security.utils.BlogSecurityUtils.getUsernameFromSecurityContext;
import static com.pysarenko.blog.security.utils.BlogSecurityUtils.isAdmin;

import com.pysarenko.blog.dto.PostDto;
import com.pysarenko.blog.mapper.PostMapper;
import com.pysarenko.blog.model.Post;
import com.pysarenko.blog.repository.PostRepository;
import com.pysarenko.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    var post = postMapper.toPost(postDto);
    post.setAuthorUsername(getUsernameFromSecurityContext());

    var savedPost = postRepository.save(post);

    log.info("Created new post with id: {} for user: {}", savedPost.getId(), savedPost.getAuthorUsername());
    return postMapper.toDto(savedPost);
  }

  @Override
  @Transactional
  public PostDto editPost(String id, PostDto post) {
    var storedPost = postRepository.findById(id)
        .filter(this::isPermitted)
        .orElseThrow();

    var postToUpdate = postMapper.updatePost(storedPost, post);
    var updatedPost = postRepository.save(postToUpdate);

    log.info("Updated post with id: {} by user: {}", updatedPost.getId(), updatedPost.getAuthorUsername());
    return postMapper.toDto(updatedPost);
  }

  @Override
  @Transactional
  public void deletePost(String id) {
    postRepository.findById(id)
        .filter(this::isPermitted)
        .ifPresent(p -> {
          postRepository.deleteById(id);
          log.info("Deleted post with id: {} by user: {}", p.getId(), p.getAuthorUsername());
        });
  }

  @Override
  @Transactional(readOnly = true)
  public PostDto getPost(String id) {
    log.info("Retrieving post with id: {} for user: {}", id, getUsernameFromSecurityContext());

    return postRepository.findById(id)
        .filter(this::isPermitted)
        .map(postMapper::toDto)
        .orElseThrow();
  }

  @Override
  @Transactional(readOnly = true)
  public Page<PostDto> getAllPosts(Pageable pageable) {
    log.info("Retrieving all posts for user: {}", getUsernameFromSecurityContext());
    var userRole = getUserProfileFromSecurityContext().getRole();

    return switch (userRole) {
      case ADMIN -> postRepository.findAll(pageable)
          .map(postMapper::toDto);
      case PUBLISHER -> postRepository.findAllByAuthorUsername(getUsernameFromSecurityContext(), pageable)
          .map(postMapper::toDto);
    };
  }

  private boolean isPermitted(Post post) {
    return post.getAuthorUsername().equals(getUsernameFromSecurityContext()) || isAdmin();
  }
}

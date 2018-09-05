package com.pisarenko.blogapi.service;

import com.pisarenko.blogapi.model.Post;

import java.util.List;

public interface PostService {

    Post createPost(Post post);

    Post editPost(Post post);

    void deletePost(long id);

    List<Post> getPostById(long id);

    List<Post> getAllPosts();

    List<Post> getAllAuthorPosts(long id);

}

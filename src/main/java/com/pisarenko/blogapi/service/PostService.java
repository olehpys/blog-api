package com.pisarenko.blogapi.service;

import com.pisarenko.blogapi.model.Post;
import com.pisarenko.blogapi.model.User;

import java.util.List;

public interface PostService {

    Post createPost(Post post, User user);

    Post editPost(Post post);

    void deletePost(long id);

    List<Post> getPostById(long id);

    List<Post> getAllPosts();

    List<Post> getAllPostsByAuthorId(long id);

}

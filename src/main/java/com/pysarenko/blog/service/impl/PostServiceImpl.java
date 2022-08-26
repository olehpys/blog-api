package com.pysarenko.blog.service.impl;

import com.pysarenko.blog.model.Post;
import com.pysarenko.blog.model.User;
import com.pysarenko.blog.repository.PostRepository;
import com.pysarenko.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post createPost(Post post, User user) {
        post.setAuthor(user);
        return postRepository.save(post);
    }

    @Override
    public Post editPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public void deletePost(long id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<Post> getPostById(long id) {
        return postRepository.findPostById(id);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> getAllPostsByAuthorId(long id) {
        return postRepository.findPostsByAuthorId(id);
    }
}

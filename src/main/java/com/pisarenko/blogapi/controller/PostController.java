package com.pisarenko.blogapi.controller;

import com.pisarenko.blogapi.model.Post;
import com.pisarenko.blogapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "posts")
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<Post> createPost(Post post) {
        Post createdPost = postService.createPost(post);
        return ResponseEntity.ok(createdPost);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    ResponseEntity<Post> editPost(@PathVariable long id) {
        List<Post> getPost = postService.getPostById(id);
        Post editedPost = postService.editPost(getPost.get(0));
        return ResponseEntity.ok(editedPost);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    public void deletePost(@PathVariable long id) {
        postService.deletePost(id);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    ResponseEntity<List<Post>> getAllPosts() {
        List<Post> allPosts = postService.getAllPosts();
        return ResponseEntity.ok(allPosts);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    ResponseEntity<List<Post>> getAllAuthorPosts(@RequestParam("authorId") long id) {
        List<Post> allAuthorPosts = postService.getAllAuthorPosts(id);
        return ResponseEntity.ok(allAuthorPosts);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    ResponseEntity<List<Post>> getPostById(@PathVariable long id) {
        List<Post> postById = postService.getPostById(id);
        return ResponseEntity.ok(postById);
    }
}

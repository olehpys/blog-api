package com.pisarenko.blogapi.controller;

import com.pisarenko.blogapi.model.Post;
import com.pisarenko.blogapi.model.User;
import com.pisarenko.blogapi.service.PostService;
import com.pisarenko.blogapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/posts")
public class PostController {

    private PostService postService;
    private UserService userService;

    @Autowired
    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    ResponseEntity<Post> createPost(@AuthenticationPrincipal Principal principal, Post post) {
        User user = userService.getUserByUsername(principal.getName());
        Post createdPost = postService.createPost(post, user);
        return ResponseEntity.ok(createdPost);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.PUT)
    ResponseEntity<Post> editPost(@AuthenticationPrincipal Principal principal, @PathVariable long id) {

        Post postToEdit = postService.getPostById(id).get(0);
        User user = userService.getUserByUsername(principal.getName());
        if (postToEdit.getAuthor().getId() == user.getId()) {
            Post editedPost = postService.editPost(postToEdit);
            return ResponseEntity.ok(editedPost);
        } else
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public void deletePost(@AuthenticationPrincipal Principal principal, @PathVariable long id) {
        Post postToDelete = postService.getPostById(id).get(0);
        User user = userService.getUserByUsername(principal.getName());
        if (postToDelete.getAuthor().getId() == user.getId()) {
            postService.deletePost(id);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    ResponseEntity<List<Post>> getAllPosts() {
        List<Post> allPosts = postService.getAllPosts();
        return ResponseEntity.ok(allPosts);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/all/author/{id}", method = RequestMethod.GET)
    ResponseEntity<List<Post>> getAllAuthorPosts(@PathVariable long id) {
        List<Post> allAuthorPosts = postService.getAllPostsByAuthorId(id);
        return ResponseEntity.ok(allAuthorPosts);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<List<Post>> getPostById(@PathVariable long id) {
        List<Post> postById = postService.getPostById(id);
        return ResponseEntity.ok(postById);
    }
}

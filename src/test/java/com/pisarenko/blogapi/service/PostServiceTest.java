package com.pisarenko.blogapi.service;


import com.pisarenko.blogapi.model.Post;
import com.pisarenko.blogapi.model.User;
import com.pisarenko.blogapi.model.UserRole;
import com.pisarenko.blogapi.repository.PostRepository;
import com.pisarenko.blogapi.service.impl.PostServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    private User user;
    private Post post;

    @Before
    public void setupPost() {
        user = new User(1L, "admin", "qweqwe", UserRole.PUBLISHER, null);

        post = new Post(1L, "Title", "Content", user);

    }

    @Test
    public void getPostByIdTest() {
        Mockito.when(postRepository.findPostById(1L)).thenReturn(Collections.singletonList(post));

        List<Post> postList = postService.getPostById(1L);

        Assert.assertEquals(Collections.singletonList(post).get(0), postList.get(0));
    }
}

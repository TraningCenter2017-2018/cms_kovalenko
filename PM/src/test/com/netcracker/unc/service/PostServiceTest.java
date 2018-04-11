package com.netcracker.unc.service;

import com.netcracker.unc.dto.PostDto;
import com.netcracker.unc.dto.UserDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/applicationContext.xml"})
public class PostServiceTest {

    @Autowired
    PostService postService;
    @Autowired
    UserService userService;


    @Test
    public void addPost() {
        UserDto user = new UserDto("Test2", "12345");
        userService.addUser(user);
        UserDto savedUser = userService.getUserByUsername("Test2");
        PostDto post = new PostDto("title", savedUser.getUserId(), 2);
        postService.addPost(post);
        PostDto savedPost = postService.getAllPostsByUserId(savedUser.getUserId()).get(0);
        Assert.assertNotNull(savedPost);
        Assert.assertEquals(savedPost.getTitle(), post.getTitle());
        Assert.assertEquals(savedPost.getUserId(), post.getUserId());
        Assert.assertEquals(savedPost.getTextId(), post.getTextId());
        postService.deletePost(savedPost.getPostId());
        userService.deleteUser(savedUser.getUserId());
    }

    @Test
    public void updateUser() {
        UserDto user = new UserDto("Test2", "12345");
        userService.addUser(user);
        UserDto savedUser = userService.getUserByUsername("Test2");
        PostDto post = new PostDto("title", savedUser.getUserId(), 2, "1, 2, 3");
        postService.addPost(post);
        PostDto savedPost = postService.getAllPostsByUserId(savedUser.getUserId()).get(0);
        Assert.assertEquals(savedPost.getTitle(), "title");
        savedPost.setTitle("New title");
        postService.updatePost(savedPost);
        savedPost = postService.getPostById(savedPost.getPostId());
        Assert.assertEquals(savedPost.getTitle(), "New title");
        postService.deletePost(savedPost.getPostId());
        userService.deleteUser(savedUser.getUserId());
    }
}
package com.localsocial.demo.controller;

import com.localsocial.demo.pojo.Post;
import com.localsocial.demo.resource.PostResource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("api")
public class PostController {

    @Autowired
    private PostResource postResource;

    @RequestMapping(value="/posts", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> createPost(@RequestBody Post newPost) {
        String status = postResource.createPost(newPost);

        if(status.equals("success")) {
            return new ResponseEntity<>("Post created!", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Post not created! " + status, HttpStatus.BAD_REQUEST);
        }

    }



    @RequestMapping(value="/posts", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public ResponseEntity<List<Post>> getPosts() {
        List<Post> postList = postResource.getPosts();

        return new ResponseEntity<>(postList, HttpStatus.OK);
    }
}

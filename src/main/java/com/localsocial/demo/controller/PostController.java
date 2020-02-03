package com.localsocial.demo.controller;

import com.localsocial.demo.dto.LikeInfo;
import com.localsocial.demo.models.Post;
import com.localsocial.demo.resource.PostResource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@Controller
@RequestMapping("api")
public class PostController {

    @Autowired
    private PostResource postResource;

    @RequestMapping(value="/posts", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> createPost(@RequestBody Post newPost) {

        if(newPost.getMessage().equals("")) {
            return new ResponseEntity<>("Post not created! Message cannot be blank!", HttpStatus.BAD_REQUEST);
        }

        String status = postResource.createPost(newPost);

        if(status.equals("success")) {
            return new ResponseEntity<>("Post created!", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Post not created! " + status, HttpStatus.BAD_REQUEST);
        }

    }


    @RequestMapping(value="/posts", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public ResponseEntity<List<Post>> getPosts(@RequestParam(name = "start") Optional<Integer> start, @RequestParam(name = "end") Optional<Integer> end, @RequestParam(name = "username") Optional<String> username) {

        List<Post> postList = postResource.getPosts(start, end, username);
        return new ResponseEntity<>(postList, HttpStatus.OK);
    }


    @RequestMapping(value="/posts/{postId}/likes", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> changeLikeStatus(@RequestBody LikeInfo likeInfo, @PathVariable(value = "postId") String postId) {

        String status = postResource.changeLikeStatus(likeInfo, postId);

        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}

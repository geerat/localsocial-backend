package com.localsocial.demo.resource;

import com.localsocial.demo.dao.PostDao;
import com.localsocial.demo.dao.PostRepo;
import com.localsocial.demo.dao.UserRepo;
import com.localsocial.demo.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class PostResource {

    @Autowired
    private PostDao postDao;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PostRepo postRepo;

    public String createPost(Post post) {


        if(!userRepo.checkUserExists(post.getUsername())) {
            return "User doesn't exist!";
        }

        post.setPostId(UUID.randomUUID().toString());
        Date now = new Date();
        post.setTimestamp(Long.toString(now.getTime() / 1000L));
        postDao.save(post);
        return "success";
    }


    public List<Post> getPosts(Optional<Integer> start, Optional<Integer> end) {

        return postRepo.getPostsInOrder(start, end);

    }
}

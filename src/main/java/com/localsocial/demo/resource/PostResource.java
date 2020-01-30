package com.localsocial.demo.resource;

import com.localsocial.demo.dao.PostDao;
import com.localsocial.demo.dao.UserDao;
import com.localsocial.demo.dao.UserRepo;
import com.localsocial.demo.pojo.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class PostResource {

    @Autowired
    private PostDao postDao;

    @Autowired
    private UserRepo userRepo;

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


    public List<Post> getPosts() {

        return (List<Post>) postDao.findAll();

    }
}

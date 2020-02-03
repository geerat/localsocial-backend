package com.localsocial.demo.resource;

import com.localsocial.demo.dao.PostDao;
import com.localsocial.demo.dao.PostRepo;
import com.localsocial.demo.dao.UserRepo;
import com.localsocial.demo.dto.LikeInfo;
import com.localsocial.demo.models.Post;
import com.sun.org.apache.xpath.internal.operations.Bool;
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
        post.setLikeCount(0);
        post.setLikes("");

        postDao.save(post);

        return "success";
    }


    public List<Post> getPosts(Optional<Integer> start, Optional<Integer> end, Optional<String> username) {


        List<Post> postList = postRepo.getPostsInOrder(start, end);

        // if username key is present then all posts should be checked to see if the username likes each post
        if(username.isPresent()) {

            for (int i = 0; i < postList.size(); i++) {

                String[] likedUsernames = postList.get(i).getLikes().split(",");
                Boolean likeStatus = false;

                for(int j = 0; j < likedUsernames.length; j++) {

                    if (likedUsernames[j].equals(username.get())) {
                        likeStatus = true;
                    }

                }
                postList.get(i).setLikeStatus(likeStatus);


            }
        }

        return postList;
    }


    // This currently has a very naive solution which should be improved
    public String changeLikeStatus(LikeInfo likeInfo, String postId) {
        Boolean likeExists = false;
        int likeExistsIndex = 0;

        Post post = postDao.findById(postId).get();

        if (post.getLikes() == null || post.getLikes().equals("")) {
            if(likeInfo.getLike()) {
                postRepo.updateLikesForPost(postId, likeInfo.getUsername(), true);
            }
        } else {

            String[] likedUsernames = post.getLikes().split(",");

            for (int i = 0; i < likedUsernames.length; i++) {
                if (likedUsernames[i].equals(likeInfo.getUsername())) {
                    likeExists = true;
                    likeExistsIndex = i;
                    // could break from loop
                }
            }

            if (likeInfo.getLike() && !likeExists) {
                String newLikes = post.getLikes() + "," + likeInfo.getUsername();

                postRepo.updateLikesForPost(postId, newLikes, true);

            } else if (likeExists && !likeInfo.getLike()) {
                String newLikes = "";
                for (int i = 0; i < likedUsernames.length; i++) {
                    if (i != likeExistsIndex) {
                        newLikes = newLikes + "," + likedUsernames[i];
                    }
                }

                newLikes = newLikes.substring(1);

                postRepo.updateLikesForPost(postId, newLikes, false);

            }
        }
        return "";
    }
}

package com.localsocial.demo.dao;

import com.localsocial.demo.models.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepo {

    List<Post> getPostsInOrder(Optional<Integer> start, Optional<Integer> end);

    Post getPostById(String postId);

    void updateLikesForPost(String postId, String newLikes, Boolean addLike);
}

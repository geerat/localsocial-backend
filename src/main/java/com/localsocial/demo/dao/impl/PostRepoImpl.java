package com.localsocial.demo.dao.impl;
import com.localsocial.demo.dao.PostRepo;
import com.localsocial.demo.models.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class PostRepoImpl implements PostRepo {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public List<Post> getPostsInOrder(Optional<Integer> start, Optional<Integer> end) {
        Query findQuery = em.createNativeQuery("SELECT * FROM posts ORDER BY timestamp DESC", Post.class);

        List<Post> postList = findQuery.getResultList();

        // Error handling for end value out of range of posts
        if(end.isPresent() && end.get() > postList.size() - 1) {
            end = Optional.of(postList.size());
        }

        if(start.isPresent() && end.isPresent()) {
            return postList.subList(start.get() - 1, end.get() -1 );
        } else if (start.isPresent()) {
            return postList.subList(start.get() -1, postList.size() - 1 );
        } else if (end.isPresent()) {
            return postList.subList(0, end.get() -1);
        } else {
            return postList;
        }

        //TODO error handling for start & end out of bounds or end < start
    }

    @Override
    @Transactional
    public Post getPostById(String postId) {
        Query findQuery = em.createNativeQuery("SELECT * FROM posts WHERE post_id = :postId", Post.class);

        findQuery.setParameter("postId", postId);

        List<Post> postList = findQuery.getResultList();

        Post post = postList.get(0);

        return post;
    }


    @Override
    @Transactional
    public void updateLikesForPost(String postId, String newLikes, Boolean addLike) {
        Post post = em.find(Post.class, postId);
        post.setLikes(newLikes);

        if(addLike) {
            post.setLikeCount(post.getLikeCount()+1);
        } else {
            post.setLikeCount(post.getLikeCount() - 1);
        }

        em.persist(post);
    }


}

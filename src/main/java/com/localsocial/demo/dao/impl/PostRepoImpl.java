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

}

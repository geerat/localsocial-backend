package com.localsocial.demo.dao;

import com.localsocial.demo.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostDao extends CrudRepository<Post, String> {
}

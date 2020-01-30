package com.localsocial.demo.dao;

import com.localsocial.demo.pojo.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostDao extends CrudRepository<Post, String> {
}

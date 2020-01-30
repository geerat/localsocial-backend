package com.localsocial.demo.dao;

import com.localsocial.demo.pojo.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, String> {
}

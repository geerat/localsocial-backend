package com.localsocial.demo.resource;

import com.localsocial.demo.dao.UserDao;
import com.localsocial.demo.dao.UserRepo;
import com.localsocial.demo.dto.LoginDto;
import com.localsocial.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class UserResource {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRepo userRepo;

    public String createUser(User userData) {

        if(!validateUsername(userData.getUsername())) {
            return "invalid username";
        }

        if(!validateUsername(userData.getPassword())) {
            return "invalid password";
        }

        String error = "success";

        String userId = UUID.randomUUID().toString();
        userData.setUserId(userId);
        try {
            userDao.save(userData);
        } catch(Exception e) {
            error = e.getMessage();
        }

        return error;
    }

    public List<User> getAllUsers() {
        List<User> userList = (List<User>)userDao.findAll();
        return userList;
    }

    public Boolean validateUser(LoginDto loginData) {
        return userRepo.validateCredentials(loginData);
    }

    public Boolean validateUsername(String username) {
        Boolean valid = true;

        if(username.length() < 5) {
            valid = false;
        }

        return valid;
    }
}

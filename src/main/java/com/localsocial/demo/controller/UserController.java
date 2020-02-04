package com.localsocial.demo.controller;
import com.localsocial.demo.dto.Auth;
import com.localsocial.demo.dto.LoginDto;
import com.localsocial.demo.models.User;
import com.localsocial.demo.resource.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("api")
public class UserController {

    @Autowired
    private UserResource userResource;

    @RequestMapping(value="/",  method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> testApi(@RequestParam(value = "status") String status) {

        return new ResponseEntity<>("API is active! You sent status: " + status, HttpStatus.OK);
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<Auth> createUser(@RequestBody User userData) {
        String status = userResource.createUser(userData);

        if (status.equals("success")) {
            return new ResponseEntity<>(new Auth(userData.getUsername(), "success"), HttpStatus.CREATED);
        } else if (status.equals("could not execute statement; SQL [n/a]; constraint [PRIMARY]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement")){
            return new ResponseEntity<>(new Auth(userData.getUsername(), "username-taken"), HttpStatus.BAD_REQUEST);
        } else if (status.equals("invalid username")) {
            return new ResponseEntity<>(new Auth(userData.getUsername(), "username-invalid"), HttpStatus.BAD_REQUEST);
        } else if (status.equals("invalid password")) {
            return new ResponseEntity<>(new Auth(userData.getUsername(), "password-invalid"), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(new Auth(userData.getUsername(), "failure"), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public ResponseEntity<List<User>> getUsers() {
        List<User> userList = userResource.getAllUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<Auth> validateUser(@RequestBody LoginDto loginData) {
        if(userResource.validateUser(loginData)) {
            return new ResponseEntity<>(new Auth(loginData.getUsername(), "success"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Auth(loginData.getUsername(), "failure"), HttpStatus.BAD_REQUEST);
        }
    }

}

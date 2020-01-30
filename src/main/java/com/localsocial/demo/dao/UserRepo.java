package com.localsocial.demo.dao;

import com.localsocial.demo.dto.LoginDto;

public interface UserRepo {

    boolean checkUserExists(String username);

    boolean validateCredentials(LoginDto loginData);
}

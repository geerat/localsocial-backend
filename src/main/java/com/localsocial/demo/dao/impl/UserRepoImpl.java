package com.localsocial.demo.dao.impl;

import com.localsocial.demo.dao.UserRepo;
import com.localsocial.demo.dto.LoginDto;
import com.localsocial.demo.pojo.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Repository
public class UserRepoImpl implements UserRepo {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public boolean checkUserExists(String username) {
        Query findQuery = em.createNativeQuery("SELECT * FROM users WHERE username = :searchUsername", User.class);
        findQuery.setParameter("searchUsername", username);

        if(findQuery.getResultList().size() == 0) {
            return false;
        }
        return true;
    }


    @Override
    @Transactional
    public boolean validateCredentials(LoginDto loginData) {
        Query findQuery = em.createNativeQuery("SELECT * FROM users WHERE username = :searchUsername", User.class);
        findQuery.setParameter("searchUsername", loginData.getUsername());

        try {
            User user = (User) findQuery.getResultList().get(0);

            if(loginData.getPassword().equals(null) || loginData.getPassword().equals("null")) {
                return false;
            }

            if(user.getPassword().equals(loginData.getPassword())) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }



    }

}

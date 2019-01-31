package com.cchao.sleep.service;

import com.cchao.sleep.dao.User;
import com.cchao.sleep.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author : cchao
 * @version 2019-01-31
 */
public class UserService {

    @Autowired
    UserRepository mUserRepository;

    public User findUserById(long id) {
        return mUserRepository.getOne(id);
    }

    public User findUserByName(String name) {
        return mUserRepository.findByName(name);
    }

}

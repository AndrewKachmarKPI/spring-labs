package com.spring.labs.lab2.service;

import com.spring.labs.lab2.dao.UserDao;
import com.spring.labs.lab2.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    public final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User findUserByName(String username) {
        return userDao.findByName(username);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }
}

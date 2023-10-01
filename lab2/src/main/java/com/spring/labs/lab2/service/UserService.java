package com.spring.labs.lab2.service;

import com.spring.labs.lab2.domain.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findUserByName(String username);
}

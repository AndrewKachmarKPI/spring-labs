package com.spring.labs.lab2.dao;

import com.spring.labs.lab2.domain.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();

    User findByName(String username);
    void generateDefaultUsers(Integer size);
}

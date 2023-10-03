package com.spring.labs.lab4.dao;


import com.spring.labs.lab4.domain.User;

import java.util.List;

public interface UserDao {
    User save(User user);

    List<User> findAll();

    User findByName(String username);
}

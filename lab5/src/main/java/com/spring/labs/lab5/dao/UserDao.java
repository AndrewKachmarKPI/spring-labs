package com.spring.labs.lab5.dao;


import com.spring.labs.lab5.domain.User;

import java.util.List;

public interface UserDao {
    User save(User user);

    List<User> findAll();

    User findByName(String username);

    Boolean existsByUsername(String username);
}

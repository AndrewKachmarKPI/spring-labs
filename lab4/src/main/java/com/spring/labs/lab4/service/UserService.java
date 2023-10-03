package com.spring.labs.lab4.service;

import com.spring.labs.lab4.domain.User;
import net.datafaker.Faker;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findUserByName(String username);

    void generateDefaultUsers(Integer size, Faker faker);
}

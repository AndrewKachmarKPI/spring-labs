package com.spring.labs.lab5.service;

import com.spring.labs.lab5.domain.User;
import com.spring.labs.lab5.dto.CreateUserDto;
import net.datafaker.Faker;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User saveUser(CreateUserDto user);
    User findUserByName(String username);

    void generateDefaultUsers(Integer size, Faker faker);
}

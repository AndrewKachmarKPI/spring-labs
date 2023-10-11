package com.spring.labs.lab6.service;

import com.spring.labs.lab6.domain.UserEntity;
import com.spring.labs.lab6.dto.UserDto;
import com.spring.labs.lab6.dto.create.CreateUserDto;
import net.datafaker.Faker;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();
    UserDto saveUser(CreateUserDto user);
    UserDto findUserByName(String username);
    Boolean existByName(String username);
}

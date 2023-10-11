package com.spring.labs.lab6.service;

import com.spring.labs.lab6.dto.UserDto;
import net.datafaker.Faker;

import java.util.List;

public interface DefaultGenerateMethods {
    void generateDefaultCategories(Integer size, Faker faker);
    List<UserDto> generateDefaultUsers(Integer size, Faker faker);
}

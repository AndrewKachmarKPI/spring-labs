package com.spring.labs.lab6.service;

import net.datafaker.Faker;

public interface DefaultGenerateMethods {
    void generateDefaultCategories(Integer size, Faker faker);
    void generateDefaultUsers(Integer size, Faker faker);
}

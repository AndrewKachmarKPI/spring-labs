package com.spring.labs.lab2;

import com.github.javafaker.Faker;
import com.spring.labs.lab2.dao.ForumCategoryDao;
import com.spring.labs.lab2.dao.UserDao;
import com.spring.labs.lab2.service.UserService;
import com.spring.labs.lab2.service.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.Locale;

@SpringBootApplication
public class Lab2Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab2Application.class, args);
    }

    @Bean
    CommandLineRunner runner(UserDao userDao, ForumCategoryDao forumCategoryDao) {
        return args -> {
            userDao.generateDefaultUsers(100);
            forumCategoryDao.generateDefaultCategories(15);
        };
    }

    @Bean
    @Scope("singleton")
    Faker dataFaker() {
        return new Faker(Locale.UK);
    }
}

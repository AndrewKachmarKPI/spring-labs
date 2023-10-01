package com.spring.labs.lab2;

import com.spring.labs.lab2.dao.ForumCategoryDao;
import com.spring.labs.lab2.dao.UserDao;
import com.spring.labs.lab2.service.ForumCategoryService;
import com.spring.labs.lab2.service.UserService;
import com.spring.labs.lab2.service.UserServiceImpl;
import net.datafaker.Faker;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.Locale;
import java.util.Random;

@SpringBootApplication
public class Lab2Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab2Application.class, args);
    }

    @Bean
    CommandLineRunner runner(UserService userDao, ForumCategoryService forumCategoryDao) {
        return args -> {
            userDao.generateDefaultUsers(100, dataFaker());
            forumCategoryDao.generateDefaultCategories(15, dataFaker());
        };
    }

    @Bean
    @Scope("singleton")
    Faker dataFaker() {
        return new Faker();
    }

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
}

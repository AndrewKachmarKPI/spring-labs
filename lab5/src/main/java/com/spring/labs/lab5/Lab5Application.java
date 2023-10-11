package com.spring.labs.lab5;

import com.spring.labs.lab5.service.ForumCategoryService;
import com.spring.labs.lab5.service.UserService;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class Lab5Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab5Application.class, args);
    }

    @Bean
    CommandLineRunner runner(UserService userService, ForumCategoryService forumCategoryService) {
        return args -> {
            userService.generateDefaultUsers(100, dataFaker());
            forumCategoryService.generateDefaultCategories(15, dataFaker());
        };
    }

    @Bean
    @Scope("singleton")
    Faker dataFaker() {
        return new Faker();
    }

}

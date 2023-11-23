package com.spring.labs.lab4;

import com.spring.labs.lab4.service.ForumCategoryService;
import com.spring.labs.lab4.service.PostService;
import com.spring.labs.lab4.service.TopicService;
import com.spring.labs.lab4.service.UserService;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class Lab4Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab4Application.class, args);
    }

    @Bean
    CommandLineRunner runner(UserService userService, ForumCategoryService forumCategoryService, TopicService topicService, PostService postService) {
        return args -> {
            userService.generateDefaultUsers(100, dataFaker());
            forumCategoryService.generateDefaultCategories(15, dataFaker());
            topicService.generateDefaultTopics(30, dataFaker());
            postService.generateDefaultPosts(100,dataFaker());
        };
    }

    @Bean
    @Scope("singleton")
    Faker dataFaker() {
        return new Faker();
    }
}

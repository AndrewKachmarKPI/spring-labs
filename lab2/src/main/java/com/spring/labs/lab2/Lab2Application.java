package com.spring.labs.lab2;


import com.spring.labs.lab2.service.ForumCategoryService;
import com.spring.labs.lab2.service.PostService;
import com.spring.labs.lab2.service.UserService;
import com.spring.labs.lab2.service.TopicService;
import com.spring.labs.lab2.service.UserService;
import com.spring.labs.lab2.service.UserServiceImpl;
import net.datafaker.Faker;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class Lab2Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab2Application.class, args);
    }

    @Bean
    CommandLineRunner runner(UserService userDao, ForumCategoryService forumCategoryDao,PostService postServiceDao, TopicService topicDao) {
        return args -> {
            userDao.generateDefaultUsers(100, dataFaker());
            forumCategoryDao.generateDefaultCategories(15, dataFaker());
            postServiceDao.generateDefaultPosts(15, dataFaker());
            topicDao.generateDefaultTopics(15, dataFaker());
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

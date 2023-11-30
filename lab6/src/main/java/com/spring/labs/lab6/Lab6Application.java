package com.spring.labs.lab6;

import com.spring.labs.lab6.service.DefaultGenerateMethods;
import com.spring.labs.lab6.service.ForumCategoryService;
import com.spring.labs.lab6.service.UserService;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class Lab6Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab6Application.class, args);
    }

    @Bean
    CommandLineRunner runner(DefaultGenerateMethods generateMethods) {
        return args -> {
            generateMethods.generateDefaultUsers(100, dataFaker());
            generateMethods.generateDefaultCategories(15, dataFaker());
            generateMethods.generateDefaultTopics(30, dataFaker());
        };
    }

    @Bean
    Faker dataFaker() {
        return new Faker();
    }

}

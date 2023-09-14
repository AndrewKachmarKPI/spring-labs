package com.example.lab1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Component
public class Second implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Second");
    }
}

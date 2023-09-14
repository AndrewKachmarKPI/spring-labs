package com.example.lab1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class First implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("First");
    }
}

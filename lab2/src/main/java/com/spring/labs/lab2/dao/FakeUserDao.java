package com.spring.labs.lab2.dao;

import com.github.javafaker.Faker;
import com.spring.labs.lab2.domain.User;
import com.spring.labs.lab2.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class FakeUserDao implements UserDao {
    private final Faker faker;
    private Map<String, User> users = new HashMap<>();

    @Override
    public List<User> findAll() {
        return users.values().stream().toList();
    }

    @Override
    public User findByName(String username) {
        return users.get(username);
    }

    @Override
    public void generateDefaultUsers(Integer size) {
        List<String> userNames = Stream.generate(() -> faker.name().username()).distinct().limit(size).toList();
        users = IntStream.range(1, size).mapToObj(index -> User.builder()
                .id((long) index)
                .username(userNames.get(index))
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .profilePicture(faker.internet().image())
                .biography(faker.lorem().sentence())
                .registrationDate(faker.date().toString())
                .role(UserRole.REGULAR_USER)
                .build()).collect(Collectors.toMap(User::getUsername, user -> user));
    }
}

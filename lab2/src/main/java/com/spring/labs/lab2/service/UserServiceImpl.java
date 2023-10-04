package com.spring.labs.lab2.service;

import com.spring.labs.lab2.dao.UserDao;
import com.spring.labs.lab2.domain.User;
import com.spring.labs.lab2.enums.UserRole;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.util.List; 
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {
    public final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User findUserByName(String username) {
        return userDao.findByName(username);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public void generateDefaultUsers(Integer size, Faker faker) {
        List<String> userNames = Stream.generate(() -> faker.name().username()).distinct().limit(size).toList();
        IntStream.range(1, size).mapToObj(index -> User.builder()
                .id((long) index)
                .username(userNames.get(index))
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .profilePicture(faker.avatar().image())
                .biography(faker.lorem().sentence())
                .registrationDate(faker.date().toString())
                .role(UserRole.REGULAR_USER)
                .build()).forEach(userDao::save);
    }
}

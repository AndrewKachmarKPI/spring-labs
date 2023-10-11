package com.spring.labs.lab4.serviceImpl;


import com.spring.labs.lab4.dao.UserDao;
import com.spring.labs.lab4.domain.User;
import com.spring.labs.lab4.enums.UserRole;
import com.spring.labs.lab4.exceptions.ResourceNotFoundException;
import com.spring.labs.lab4.service.UserService;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        return Optional.ofNullable(userDao.findByName(username))
                .orElseThrow(() -> new ResourceNotFoundException("User with name:" + username + " is not found"));
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
